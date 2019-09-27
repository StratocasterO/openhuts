package com.omar.openhuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Lists extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		TextView tv = findViewById(R.id.title);
		tv.setText("Personal lists");

		ArrayList<Hut> huts = getHuts();

		ListAdapter adapter = new ListAdapter(this, huts);

		ListView lv = findViewById(R.id.list);
		lv.setAdapter(adapter);
	}

	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}

	public void close(View v){
		Log.d("click", "clicked on back");
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("EXIT", true);
		startActivity(intent);
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		LatLng loc = new LatLng(42, 1);
		Huts.add(new Hut(1, "hut1 name", 4.5f, new LatLng(42.0,1.0), "img1"));
		Huts.add(new Hut(2, "hut2 name", 4.0f, new LatLng(43.0,1.0), "img2"));
		Huts.add(new Hut(3, "hut3 name", 2.5f, new LatLng(41.0,1.0), "img3"));
		Huts.add(new Hut(4, "hut4 name", 1.5f, new LatLng(42.0,2.0), "img4"));
		Huts.add(new Hut(5, "hut5 name", 5f, new LatLng(42.0,0.0), "img5"));

		return Huts;
	}

	// TODO add lists and buttons to rearrange and delete lists
	// TODO unify lists and favourites
	// TODO adapter element of one list
}
