/* PreferenceHelper.java
   A port of LAME for Android

   Copyright (c) 2010 Ethan Chen

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2 of the License, or (at your option) any later version.
	
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.intervigil.lame.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.intervigil.lame.R;

public class PreferenceHelper {

    public static boolean getShowAds(Context context) {
        SharedPreferences prefReader = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean pref = prefReader.getBoolean(context
                .getString(R.string.prefs_enable_ads_key), Boolean
                .parseBoolean(context
                        .getString(R.string.prefs_enable_ads_default)));
        return pref;
    }
}
