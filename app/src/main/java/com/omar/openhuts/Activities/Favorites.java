package com.omar.openhuts.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.omar.openhuts.Tools.HutAdapter;
import com.omar.openhuts.POJOs.Hut;
import com.omar.openhuts.POJOs.HutList;
import com.omar.openhuts.R;

import java.util.ArrayList;

public class Favorites extends DefaultActivity {
	ListView lv;
	ArrayList<Hut> huts;

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

		huts = getHuts();

		HutAdapter adapter = new HutAdapter(this, huts);

		lv = findViewById(R.id.list);
		lv.setAdapter(adapter);
	}

	public void hutClicked(View v){
		int id_hut = (int) v.getTag(R.string.hut);
		startActivity(new Intent(Favorites.this, HutPage.class)
				.putExtra("hut", id_hut));
		overridePendingTransition(0,0);
	}

	public void delete(View v){
		lv = findViewById(R.id.list);
		if (v.getBackground() != null && v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.cor_full).getConstantState())){
			v.setBackgroundResource(R.drawable.cor_empty);
		} else {
			v.setBackgroundResource(R.drawable.cor_full);
		}
		View item = (View) v.getParent();
		int pos = lv.getPositionForView(item);
		Log.d("click", "clicked on delete of item " + pos);

		// TODO delete item from list
	}

	// Lists hardcoded for testing
	public ArrayList<HutList> getLists() {
		ArrayList<HutList> Lists = new ArrayList<HutList>();

		Lists.add(new HutList("Favorites", getHuts(), 1)); // TODO fix lists
		Lists.add(new HutList("List 1", getHuts(), 2));
		Lists.add(new HutList("List 2", getHuts(), 3));
		Lists.add(new HutList("List 3", getHuts(), 4));
		Lists.add(new HutList("List 4", getHuts(), 5));

		return Lists;
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		Huts.add(new Hut(2, "hut2 name", "", 4.0f, new LatLng(43.0, 1.0), 1, 1, 1, "img2", "http://openhuts.com"));
		Huts.add(new Hut(1, "hut1 name", "", 4.5f, new LatLng(42.0, 1.0), 1, 1, 1, "img1", "http://openhuts.com"));
		Huts.add(new Hut(3, "hut3 name", "", 2.5f, new LatLng(41.0, 1.0), 1, 1, 1, "img3", "http://openhuts.com"));
		Huts.add(new Hut(4, "hut4 name", "", 1.5f, new LatLng(42.0, 2.0), 1, 1, 1, "img4", "http://openhuts.com"));
		Huts.add(new Hut(5, "hut5 name", "", 5f, new LatLng(42.0, 0.0), 1, 1, 1, "img5", "http://openhuts.com"));

		return Huts;
	}
}
