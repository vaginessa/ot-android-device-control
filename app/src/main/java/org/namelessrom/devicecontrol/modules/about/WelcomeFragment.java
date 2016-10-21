/*
 *  Copyright (C) 2013 - 2014 Alexander "Evisceration" Martinz
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
package org.namelessrom.devicecontrol.modules.about;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.namelessrom.devicecontrol.App;
import org.namelessrom.devicecontrol.R;
import org.namelessrom.devicecontrol.utils.DrawableHelper;

public class WelcomeFragment extends Fragment {
    private static final String URL_TRANSLATION = "https://crowdin.com/project/devicecontrol";

    @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about, container, false);

        final TextView details = (TextView) view.findViewById(R.id.detailsTxtView);
        details.setText(getString(R.string.welcome_message, getString(R.string.app_name)));

        final Drawable translateDrawable = DrawableHelper.getDrawable(R.drawable.ic_translate_black_24dp);
        final Button translateButton = (Button) view.findViewById(R.id.translateButton);
        translateButton.setCompoundDrawablesWithIntrinsicBounds(
                DrawableHelper.applyAccentColorFilter(translateDrawable), null, null, null);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final Activity activity = getActivity();
                ((App) activity.getApplicationContext()).getCustomTabsHelper().launchUrl(activity, URL_TRANSLATION);
            }
        });

        return view;
    }
}
