package com.omar.openhuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Login extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}

	public void close(View v){
		Log.d("click", "clicked on back");
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}

	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class));
	}

	public void forgot(View v){
		Log.d("click", "clicked on forgot my pass");
	}

	public void login(View v){
		Log.d("click", "clicked on button login");
	}
}
