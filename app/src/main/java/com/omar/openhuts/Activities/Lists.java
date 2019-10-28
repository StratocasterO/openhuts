package com.omar.openhuts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.omar.openhuts.POJOs.Hut;
import com.omar.openhuts.POJOs.HutList;
import com.omar.openhuts.R;
import com.omar.openhuts.Tools.ListAdapter;

import java.util.ArrayList;

import static com.omar.openhuts.R.id.list;

public class Lists extends DefaultActivity {
	ListView lv;
	boolean pressed = false;
	ListAdapter adapter;
	ArrayList<HutList> lists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		TextView tv = findViewById(R.id.title);
		tv.setText("Saved huts");

		lists = getLists();

		adapter = new ListAdapter(this, lists,false);

		lv = findViewById(list);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int id_list = lists.get(position).getId();
				startActivity(new Intent(Lists.this, Favorites.class)
						.putExtra("list", id_list));
			}
		});
	}

	// TODO activates and deactivates edit mode (doesn't work)
	public void edit(View v) {
		Log.d("click", "clicked on edit");
		lv = findViewById(list);
		if (!pressed) {
			adapter = new ListAdapter(this, lists,true);
			lv.setAdapter(adapter);
			pressed = true;
		} else {
			adapter = new ListAdapter(this, lists,false);
			lv.setAdapter(adapter);
			pressed = false;
		}
	}

	// TODO delete list when pressing button
	public void delete(View v) {
		View item = (View) v.getParent();
		int pos = lv.getPositionForView(item);
		Log.d("click", "clicked on delete of item " + pos);
	}

	// Lists hardcoded for testing
	public ArrayList<HutList> getLists() {
		ArrayList<HutList> Lists = new ArrayList<HutList>();

		Lists.add(new HutList("Favorites", getHuts(), 1));
		Lists.add(new HutList("List 1", getHuts(), 2));
		Lists.add(new HutList("List 2", getHuts(), 3));
		Lists.add(new HutList("List 3", getHuts(), 4));
		Lists.add(new HutList("List 4", getHuts(), 5));

		return Lists;
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		Huts.add(new Hut(1, "hut1 name", "", 4.5f, new LatLng(42.0, 1.0), 1, 1, 1, "img1","http://openhuts.com"));
		Huts.add(new Hut(2, "hut2 name", "", 4.0f, new LatLng(43.0, 1.0), 1, 1, 1, "img2","http://openhuts.com"));
		Huts.add(new Hut(3, "hut3 name", "", 2.5f, new LatLng(41.0, 1.0), 1, 1, 1, "img3","http://openhuts.com"));
		Huts.add(new Hut(4, "hut4 name", "", 1.5f, new LatLng(42.0, 2.0), 1, 1, 1, "img4","http://openhuts.com"));
		Huts.add(new Hut(5, "hut5 name", "", 5f, new LatLng(42.0, 0.0), 1, 1, 1, "img5","http://openhuts.com"));

		return Huts;
	}
}
