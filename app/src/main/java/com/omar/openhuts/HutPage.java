package com.omar.openhuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HutPage extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hut_page);

		TextView tv = findViewById(R.id.title);
		tv.setText("Hut");
	}

	// TODO hut layout
}
