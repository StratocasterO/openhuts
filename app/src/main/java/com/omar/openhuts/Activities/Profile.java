package com.omar.openhuts.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.omar.openhuts.R;

public class Profile extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		ImageView iv = findViewById(R.id.photo);

		// Makes image round
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hut);
		RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
		circularBitmapDrawable.setCircular(true);
		iv.setImageDrawable(circularBitmapDrawable);
	}

	public void logout(View v){
		settings.edit().putBoolean("logged", false).apply();
		finish();
		overridePendingTransition(0,0);
	}
}
