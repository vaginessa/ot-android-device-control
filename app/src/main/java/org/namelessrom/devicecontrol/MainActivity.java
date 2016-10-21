/*
 *  Copyright (C) 2013 - 2016 Alexander "Evisceration" Martinz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.namelessrom.devicecontrol;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import org.namelessrom.devicecontrol.activities.BaseActivity;
import org.namelessrom.devicecontrol.listeners.OnBackPressedListener;
import org.namelessrom.devicecontrol.models.AppResources;
import org.namelessrom.devicecontrol.modules.appmanager.AppListFragment;
import org.namelessrom.devicecontrol.modules.preferences.PreferencesActivity;
import org.namelessrom.devicecontrol.utils.AppHelper;
import org.namelessrom.devicecontrol.utils.Utils;

import at.amartinz.execution.ShellManager;
import timber.log.Timber;

public class MainActivity extends BaseActivity {
    public static boolean sDisableFragmentAnimations;
    private static long mBackPressed;
    private Toast mToast;
    private Fragment mCurrentFragment;

    private int mTitle = R.string.home;
    private int mFragmentTitle = R.string.home;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
//        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadFragmentPrivate();
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    protected void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ViewCompat.setElevation(toolbar, 4.0f);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCustomBackPressed();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PreferencesActivity.REQUEST_PREFERENCES == requestCode) {
            if (resultCode == PreferencesActivity.RESULT_NEEDS_RESTART) {
                // restart the activity and cleanup AppResources to update effects and theme
                AppResources.get().cleanup();
                Utils.restartActivity(MainActivity.this);
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        restoreActionBar();
        return super.onCreateOptionsMenu(menu);
    }

    private void restoreActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mTitle);
        }
    }

    /**
     * Start activity setting
     */
    public void gotoSetting() {
        final Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        startActivityForResult(intent, PreferencesActivity.REQUEST_PREFERENCES);
    }


    private void onCustomBackPressed() {

        // if we have a OnBackPressedListener at the fragment, go in
        if (mCurrentFragment instanceof OnBackPressedListener) {
            final OnBackPressedListener listener = ((OnBackPressedListener) mCurrentFragment);

            // if our listener handles onBackPressed(), return
            if (listener.onBackPressed()) {
                Timber.v("onBackPressed() handled by current fragment");
                return;
            }
        }

        // we we have at least one fragment in the BackStack, pop it and return
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            mTitle = mFragmentTitle;
            restoreActionBar();

            return;
        }

        // if nothing matched by now, we do not have any fragments in the BackStack, nor we have
        // the menu open. in that case lets detect a double back press and exit the activity
        if (mBackPressed + 2000 > System.currentTimeMillis()) {
            if (mToast != null) {
                mToast.cancel();
            }
            finish();
        } else {
            mToast = Toast.makeText(getBaseContext(),
                    getString(R.string.action_press_again), Toast.LENGTH_SHORT);
            mToast.show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        onCustomBackPressed();
    }

    @Override
    protected void onDestroy() {
        Timber.d("closing shells");
        ShellManager.get().cleanupShells();

        AppResources.get().cleanup();
        super.onDestroy();
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    public void setFragment(final Fragment fragment) {
        if (fragment == null) {
            return;
        }
        mCurrentFragment = fragment;
    }

    private void loadFragmentPrivate() {
        mCurrentFragment = new AppListFragment();
        mTitle = mFragmentTitle = R.string.app_manager;

        restoreActionBar();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // set a lock to prevent calling setFragment as onResume gets called
            AppHelper.preventOnResume = true;
            MainActivity.sDisableFragmentAnimations = true;
            try {
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalArgumentException ignored) {
            }
            MainActivity.sDisableFragmentAnimations = false;
            // release the lock
            AppHelper.preventOnResume = false;
        }

        final FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.container, mCurrentFragment);
        ft.commit();

    }

}
