package com.omar.openhuts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Register extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	public void register(View v) {
		Log.d("click", "clicked on send register");
	}
}
