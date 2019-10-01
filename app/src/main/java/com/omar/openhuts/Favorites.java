package com.omar.openhuts;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Favorites extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		int list = getIntent().getExtras().getInt("list");

		TextView tv = findViewById(R.id.title);
		for(List l : getLists()){
			if(l.getId() == list){
				tv.setText(l.getName());
			}
		}

		ArrayList<Hut> huts = getHuts();

		HutAdapter adapter = new HutAdapter(this, huts);

		ListView lv = findViewById(R.id.list);
		lv.setAdapter(adapter);
	}

	// Lists hardcoded for testing
	public ArrayList<List> getLists() {
		ArrayList<List> Lists = new ArrayList<List>();

		Lists.add(new List("favorites", getHuts(),1));
		Lists.add(new List("list 1", getHuts(),2));
		Lists.add(new List("list 2", getHuts(),3));
		Lists.add(new List("list 3", getHuts(),4));
		Lists.add(new List("list 4", getHuts(),5));

		return Lists;
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		Huts.add(new Hut(1, "hut1 name", 4.5f, new LatLng(42.0,1.0), "img1"));
		Huts.add(new Hut(2, "hut2 name", 4.0f, new LatLng(43.0,1.0), "img2"));
		Huts.add(new Hut(3, "hut3 name", 2.5f, new LatLng(41.0,1.0), "img3"));
		Huts.add(new Hut(4, "hut4 name", 1.5f, new LatLng(42.0,2.0), "img4"));
		Huts.add(new Hut(5, "hut5 name", 5f, new LatLng(42.0,0.0), "img5"));

		return Huts;
	}

	// TODO add buttons for rearranging and deleting huts
}
