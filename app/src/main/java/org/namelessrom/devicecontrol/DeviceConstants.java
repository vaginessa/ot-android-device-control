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
package org.namelessrom.devicecontrol;

import java.io.File;

public class DeviceConstants {

    //==============================================================================================
    // IDs
    //==============================================================================================
    public static final int ID_PGREP = Integer.MAX_VALUE - 1000;

    //==============================================================================================
    // Fragments
    //==============================================================================================
    public static final int ID_HOME = R.id.nav_item_home;
    public static final int ID_TOOLS_APP_MANAGER = R.id.nav_item_tools_app_manager;
    //--- APP INFO
    public static final int ID_APP_INFO_LICENSE = R.id.nav_item_app_info_license;
    public static final int ID_APP_INFO_PRIVACY = R.id.nav_item_app_info_privacy;
    //----------------------------------------------------------------------------------------------

    //==============================================================================================
    // URLs
    //==============================================================================================
    public static final String URL_DC_LICENSE = "https://evisceration.github.io/devicecontrol/license.html";
    public static final String URL_DC_PRIVACY = "https://evisceration.github.io/devicecontrol/privacy.html";

    //==============================================================================================
    // Directories
    //==============================================================================================
    public static final String DC_LOG_DIR = File.separator + "Logs";
    //==============================================================================================
    public static final String DC_LOG_FILE_FSTRIM = DC_LOG_DIR + File.separator + "fstrim.log";

    //==============================================================================================
    // Donations
    //==============================================================================================
    /** Donation  2€ * */
    public static final String SKU_DONATION_1 = "donation_1";
    /** Donation  5€ * */
    public static final String SKU_DONATION_2 = "donation_2";
    /** Donation 10€ * */
    public static final String SKU_DONATION_3 = "donation_3";
    /** Donation 20€ * */
    public static final String SKU_DONATION_4 = "donation_4";
    /** Donation 50€ * */
    public static final String SKU_DONATION_5 = "donation_5";

    /** Donation, subscription  2€ * */
    public static final String SUB_DONATION_1 = "donation_sub_1";
    /** Donation, subscription  5€ */
    public static final String SUB_DONATION_2 = "donation_sub_2";
    /** Donation, subscription 10€ * */
    public static final String SUB_DONATION_3 = "donation_sub_3";
    /** Donation, subscription 20€ * */
    public static final String SUB_DONATION_4 = "donation_sub_4";
    /** Donation, subscription 50€ * */
    public static final String SUB_DONATION_5 = "donation_sub_5";
}
