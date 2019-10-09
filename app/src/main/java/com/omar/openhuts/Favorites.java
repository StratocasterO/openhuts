package com.omar.openhuts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Favorites extends DefaultActivity {
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		int list = getIntent().getExtras().getInt("list");

		TextView tv = findViewById(R.id.title);
		for (HutList l : getLists()) {
			if (l.getId() == list) {
				tv.setText(l.getName());
			}
		}

		Button btn = findViewById(R.id.edit);
		btn.setVisibility (View.GONE);
		btn.setEnabled(false);

		ArrayList<Hut> huts = getHuts();

		HutAdapter adapter = new HutAdapter(this, huts);

		ListView lv = findViewById(R.id.list);
		lv.setAdapter(adapter);
	}

	public void delete(View v){
		View item = (View) v.getParent();
		int pos = lv.getPositionForView(item);
		Log.d("click", "clicked on delete of item " + pos);

		// TODO delete item from list
		// TODO change heart icon to gray/red
	}

	// Lists hardcoded for testing
	public ArrayList<HutList> getLists() {
		ArrayList<HutList> Lists = new ArrayList<HutList>();

		Lists.add(new HutList("favorites", getHuts(), 1));
		Lists.add(new HutList("list 1", getHuts(), 2));
		Lists.add(new HutList("list 2", getHuts(), 3));
		Lists.add(new HutList("list 3", getHuts(), 4));
		Lists.add(new HutList("list 4", getHuts(), 5));

		return Lists;
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		Huts.add(new Hut(1, "hut1 name", "", 4.5f, new LatLng(42.0, 1.0), 1, 1, 1, "img1"));
		Huts.add(new Hut(2, "hut2 name", "", 4.0f, new LatLng(43.0, 1.0), 1, 1, 1, "img2"));
		Huts.add(new Hut(3, "hut3 name", "", 2.5f, new LatLng(41.0, 1.0), 1, 1, 1, "img3"));
		Huts.add(new Hut(4, "hut4 name", "", 1.5f, new LatLng(42.0, 2.0), 1, 1, 1, "img4"));
		Huts.add(new Hut(5, "hut5 name", "", 5f, new LatLng(42.0, 0.0), 1, 1, 1, "img5"));

		return Huts;
	}
}
