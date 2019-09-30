package com.omar.openhuts;

import android.os.Bundle;
import android.widget.TextView;

public class AddHut extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_hut);

		TextView tv = findViewById(R.id.title);
		tv.setText("Hut");
	}

	// TODO add hut layout (Marketing)
}
