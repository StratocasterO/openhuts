package com.omar.openhuts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);

		ArrayList<Hut> huts = getHuts();

		MyAdapter adapter = new MyAdapter(huts);

		ListView lv = findViewById(R.id.list);
		lv.setAdapter(adapter);
	}
	
	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class));
	}

	public void close(View v){
		Log.d("click", "clicked on back");
		startActivity(new Intent(this, MainActivity.class));
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		LatLng loc = new LatLng(42, 1);
		Huts.add(new Hut(1, "hut1", 4.5f, new LatLng(42.0,1.0), "img1"));
		Huts.add(new Hut(2, "hut2", 4.0f, new LatLng(43.0,1.0), "img2"));
		Huts.add(new Hut(3, "hut3", 2.5f, new LatLng(41.0,1.0), "img3"));
		Huts.add(new Hut(4, "hut4", 1.5f, new LatLng(42.0,2.0), "img4"));
		Huts.add(new Hut(5, "hut5", 5f, new LatLng(42.0,0.0), "img5"));

		return Huts;
	}

	private class MyAdapter extends android.widget.BaseAdapter {
		ArrayList<Hut> Huts;

		public MyAdapter(ArrayList<Hut> Huts) {
			super();
			this.Huts = Huts;
		}

		@Override
		public int getCount() {
			return Huts.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		public View getView(int index, View convertView, ViewGroup parent) {
			View row = View.inflate(Favorites.this, R.layout.list_layout, null);
			TextView tv = row.findViewById(R.id.name);
			tv.setText(Huts.get(index).getName());

			// Compute the distance from the user to the hut and show it in tv2:
			/*TextView tv2 = row.findViewById(R.id.distance);
			tv2.setText("~ " + Huts.get(position).getDistance().toString()+" km");*/

			// Rating:
			RatingBar rb = row.findViewById(R.id.rating);
			rb.setRating(Huts.get(index).getRating());

			return row;
		}
	}
}
