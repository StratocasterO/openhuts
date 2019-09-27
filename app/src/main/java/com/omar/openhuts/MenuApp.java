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
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}

	public void close(View v){
		Log.d("click", "clicked on back");
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		//intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);

		overridePendingTransition(0,0); // TODO extend own class instead of AppCompatActivity
	}

	public void login(View v){
		Log.d("click", "clicked on login");
		startActivity(new Intent(this, Login.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	public void register(View v){
		Log.d("click", "clicked on register");
		startActivity(new Intent(this, Register.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	public void fav(View v){
		Log.d("click", "clicked on favorites");
		startActivity(new Intent(this, Favorites.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	public void lists(View v){
		Log.d("click", "clicked on lists");
		startActivity(new Intent(this, Lists.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	// function to remove animations between activities
	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(0, 0);
	}

	// TODO add profile button
}
