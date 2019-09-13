package com.omar.openhuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Register extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class));
	}

	public void close(View v){
		Log.d("click", "clicked on back");
		startActivity(new Intent(this, MainActivity.class));
	}

	public void register(View v) {
		Log.d("click", "clicked on send register");
		startActivity(new Intent(this, MenuApp.class));
	}
}
