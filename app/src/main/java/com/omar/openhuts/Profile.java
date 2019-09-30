package com.omar.openhuts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

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
}
