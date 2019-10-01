package com.omar.openhuts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Lists extends DefaultActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		TextView tv = findViewById(R.id.title);
		tv.setText("Saved huts");

		final ArrayList<List> lists = getLists();

		ListAdapter adapter = new ListAdapter(this, lists);

		final ListView lv = findViewById(R.id.list);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int id_list = lists.get(position).getId();
				startActivity(new Intent(Lists.this, Favorites.class)
						.putExtra("list",id_list));
			}
		});
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

	// TODO add lists and buttons to rearrange and delete lists
}
