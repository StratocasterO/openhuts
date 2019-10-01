package com.omar.openhuts;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DefaultActivity extends AppCompatActivity {
	@Override
	public void onBackPressed() {
		finish();

		overridePendingTransition(0,0);
		}

	public void close(View v){
		Log.d("click", "clicked on back");
		finish();

		overridePendingTransition(0,0);
	}

	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class));

		overridePendingTransition(0,0);
	}

	// function to remove animations between activities
	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(1, 1);
	}
}
