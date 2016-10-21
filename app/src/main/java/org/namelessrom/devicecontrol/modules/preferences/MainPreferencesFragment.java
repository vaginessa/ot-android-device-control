/*
 *  Copyright (C) 2013 - 2015 Alexander "Evisceration" Martinz
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
package org.namelessrom.devicecontrol.modules.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import org.namelessrom.devicecontrol.App;
import org.namelessrom.devicecontrol.models.Constants;
import org.namelessrom.devicecontrol.R;
import org.namelessrom.devicecontrol.models.DeviceConfig;
import org.namelessrom.devicecontrol.models.AppResources;
import org.namelessrom.devicecontrol.utils.Utils;

import alexander.martinz.libs.materialpreferences.MaterialListPreference;
import alexander.martinz.libs.materialpreferences.MaterialPreference;
import alexander.martinz.libs.materialpreferences.MaterialSupportPreferenceFragment;
import alexander.martinz.libs.materialpreferences.MaterialSwitchPreference;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPreferencesFragment extends MaterialSupportPreferenceFragment implements MaterialPreference.MaterialPreferenceChangeListener {
    // TODO: more customization
    @BindView(R.id.prefs_theme_mode) MaterialListPreference themeMode;
    @BindView(R.id.prefs_low_end_gfx) MaterialSwitchPreference lowEndGfx;


    @Override protected int getLayoutResourceId() {
        return R.layout.pref_app_main;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final Context context = getContext();
        final DeviceConfig configuration = DeviceConfig.get();

        themeMode.setValueIndex(DeviceConfig.get().themeMode - 1);
        themeMode.setOnPreferenceChangeListener(this);

        lowEndGfx.setChecked(AppResources.get(context).isLowEndGfx(context));
        lowEndGfx.setOnPreferenceChangeListener(this);

    }

    @SuppressLint("CommitPrefEdits")
    @Override public boolean onPreferenceChanged(MaterialPreference preference, Object newValue) {
        if (themeMode == preference) {
            final int mode = Utils.tryValueOf(String.valueOf(newValue), -1);
            if (mode == -1) {
                return false;
            }
            AppResources.get(getContext()).setThemeMode(mode);
            themeMode.setValueIndex(mode - 1);

            App.setupThemeMode();
            if (getActivity() instanceof PreferencesActivity) {
                ((PreferencesActivity) getActivity()).needsRestart();
            }
            return true;
        } else if (lowEndGfx == preference) {
            final boolean isLowEndGfx = (Boolean) newValue;
            AppResources.get(getContext().getApplicationContext()).setLowEndGfx(isLowEndGfx);
            lowEndGfx.setChecked(isLowEndGfx);

            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
            prefs.edit().putBoolean(Constants.KEY_LOW_END_GFX, isLowEndGfx).commit();

            if (getActivity() instanceof PreferencesActivity) {
                ((PreferencesActivity) getActivity()).needsRestart();
            }
            return true;
        }

        return false;
    }

}
