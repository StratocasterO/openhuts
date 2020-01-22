package com.omar.openhuts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.omar.openhuts.R;

public class Splash extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Thread welcomeThread = new Thread() {

			@Override
			public void run() {
				try {
					super.run();
					sleep(500);  //Delay of 1/2 second
				} catch (Exception e) {

				} finally {

					Intent i = new Intent(Splash.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					finish();
				}
			}
		};
		welcomeThread.start();
	}
}