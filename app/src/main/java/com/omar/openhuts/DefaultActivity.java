package com.omar.openhuts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DefaultActivity extends AppCompatActivity {
	static SharedPreferences settings;
	static String prefs = "MyPrefsFile";

	@Override
	public void onBackPressed() {
		finish();

		overridePendingTransition(0,0);
		}

	// function to remove animations between activities
	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(1, 1);
	}

	// checks if logged or not
	public boolean logged(){
		settings = getSharedPreferences(prefs, 0);
		boolean log = settings.getBoolean("logged", false);
		return log;
	}

	// forgotten password
	public void forgot(View v){
		Log.d("click", "clicked on forgot my pass");
		// TODO forgotten password mail (?)
	}

	// terms and conditions
	public void terms(View v){
		// TODO add link to terms and conditions
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.openhuts.com"));
		startActivity(browserIntent);
	}
}
