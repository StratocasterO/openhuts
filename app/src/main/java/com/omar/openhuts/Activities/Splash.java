package com.omar.openhuts.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.omar.openhuts.R;

public class Splash extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		ImageView panel_IMG_back = findViewById(R.id.imageView10);
		Glide.with(this).load(R.drawable.mountain).into(panel_IMG_back);

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