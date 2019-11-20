package com.omar.openhuts.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.omar.openhuts.R;

public class DefaultActivity extends AppCompatActivity {
	static SharedPreferences settings;
	static String prefs = "MyPrefsFile";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences(prefs, 0);
	}

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

	// feature in development
	public void wip(View v){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				this);
		alertDialog.setTitle("Work in progress");
		alertDialog.setMessage("The feature you are trying to use is under development at this moment.\n\nPlease, keep updating the app if you want to see new features like this.");
		alertDialog.setIcon(R.drawable.logo);
		alertDialog.setNegativeButton("OKAY",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}
}
