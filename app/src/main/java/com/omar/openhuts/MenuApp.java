package com.omar.openhuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuApp extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
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

	public void login(View v){
		Log.d("click", "clicked on login");
		startActivity(new Intent(this, Login.class));
	}

	public void register(View v){
		Log.d("click", "clicked on register");
		startActivity(new Intent(this, Register.class));
	}

	public void fav(View v){
		Log.d("click", "clicked on favorites");
		startActivity(new Intent(this, Favorites.class));
	}

	public void lists(View v){
		Log.d("click", "clicked on lists");
		startActivity(new Intent(this, Lists.class));
	}
}
