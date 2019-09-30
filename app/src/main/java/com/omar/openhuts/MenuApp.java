package com.omar.openhuts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuApp extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	public void login(View v) {
		Log.d("click", "clicked on login");
		startActivity(new Intent(this, Login.class));
		finish();
	}

	public void register(View v) {
		Log.d("click", "clicked on register");
		startActivity(new Intent(this, Register.class));
		finish();
	}

	public void lists(View v) {
		Log.d("click", "clicked on lists");
		startActivity(new Intent(this, Lists.class));
		finish();
	}

	public void profile(View v) {
		Log.d("click", "clicked on profile");
		startActivity(new Intent(this, Profile.class));
		finish();
	}
}
