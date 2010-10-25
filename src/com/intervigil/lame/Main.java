/* Main.java
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

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.admob.android.ads.AdView;
import com.intervigil.lame.helper.PreferenceHelper;
import com.intervigil.lame.intents.FileManagerIntents;

public class Main extends Activity implements OnClickListener {
	
	private Boolean showAds;
	private AdView ad;
	private EditText inputFilename;
	private EditText outputFilename;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        showAds = PreferenceHelper.getShowAds(Main.this);
        ad = (AdView) findViewById(R.id.main_adview);
        ad.setEnabled(showAds);
        
        inputFilename = (EditText) findViewById(R.id.input_file);
        outputFilename = (EditText) findViewById(R.id.output_file);
        
        ((Button) findViewById(R.id.encode_btn)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.select_input_file_btn)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.select_output_file_btn)).setOnClickListener(this);
    }
    
    @Override
    protected void onStart() {
        Log.i(getPackageName(), "onStart()");
        super.onStart();
    }
    
    @Override
    protected void onResume() {
    	Log.i(getPackageName(), "onResume()");
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	Log.i(getPackageName(), "onPause()");
    	super.onPause();
    }
    
    @Override
    protected void onStop() {
    	Log.i(getPackageName(), "onStop()");
    	super.onStop();
    }

    @Override
    protected void onDestroy() {
    	Log.i(getPackageName(), "onDestroy()");
    	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options:
            	Intent preferencesIntent = new Intent(getBaseContext(), Preferences.class);
            	startActivity(preferencesIntent);
            	break;
            case R.id.help:
            	
            	break;
            case R.id.about:
            	
            	break;
            case R.id.quit:
            	finish();
            	break;
        }
        return true;
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case Constants.REQUEST_CODE_OPEN_FILE:
				if (resultCode == RESULT_OK && data != null) {
					// obtain the filename
					String filename = data.getDataString();
					if (filename != null) {
						// Get rid of URI prefix:
						if (filename.startsWith("file://")) {
							filename = filename.substring(7);
						}
						inputFilename.setText(filename);
					}				
				}
				break;
			case Constants.REQUEST_CODE_SAVE_FILE:
				if (resultCode == RESULT_OK && data != null) {
					// obtain the filename
					String filename = data.getDataString();
					if (filename != null) {
						// Get rid of URI prefix:
						if (filename.startsWith("file://")) {
							filename = filename.substring(7);
						}
						outputFilename.setText(filename);
					}				
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(Main.this, String.format("got click from %d", v.getId()), Toast.LENGTH_SHORT);
		switch (v.getId()) {
			case R.id.encode_btn:
				// start async task to read/encode/write mp3
				break;
			case R.id.select_input_file_btn:
				openFile(inputFilename.getText().toString());
				break;
			case R.id.select_output_file_btn:
				saveFile(outputFilename.getText().toString());
				break;
		}

	}

	/**
	 * Opens the file manager to select a file to open.
	 */
	private void openFile(String fileName) {		
		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

		// Construct URI from file name.
		intent.setData(Uri.parse("file://" + fileName));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE, getString(R.string.open_input_file_title));
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT, getString(R.string.open_input_file_btn_text));

		try {
			startActivityForResult(intent, Constants.REQUEST_CODE_OPEN_FILE);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
		}
	}
	
	/**
	 * Opens the file manager to select a location for saving a file.
	 */
	private void saveFile(String fileName) {
		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);
		
		// Construct URI from file name.
		intent.setData(Uri.parse("file://" + fileName));
		
		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE, getString(R.string.save_output_file_title));
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT, getString(R.string.save_output_file_btn_text));
		
		try {
			startActivityForResult(intent, Constants.REQUEST_CODE_SAVE_FILE);
		} catch (ActivityNotFoundException e) {
			// No compatible file manager was found.
			
		}
	}
}