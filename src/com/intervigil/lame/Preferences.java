/* Preferences.java
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

package com.intervigil.lame;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource.
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    protected void onStart() {
        Log.i("Preferences", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("Preferences", "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("Preferences", "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("Preferences", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("Preferences", "onDestroy()");
        super.onStop();
    }
}
