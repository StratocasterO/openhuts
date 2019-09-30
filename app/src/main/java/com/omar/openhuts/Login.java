package com.omar.openhuts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Login extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	public void forgot(View v){
		Log.d("click", "clicked on forgot my pass");
	}

	public void login(View v){
		Log.d("click", "clicked on button login");
	}

	// TODO login
	// TODO login with Facebook
	// TODO login with Google
}
