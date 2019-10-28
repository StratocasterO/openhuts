package com.omar.openhuts.Activities;

import android.os.Bundle;
import android.widget.TextView;

import com.omar.openhuts.R;

public class AddHut extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_hut);

		TextView tv = findViewById(R.id.title);
		tv.setText("Hut");
	}

	// TODO https://pablomonteserin.com/curso/android/ "Subida de una imagen de la galería al servidor con Retrofit"
}
