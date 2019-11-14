package com.omar.openhuts.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.omar.openhuts.POJOs.Hut;
import com.omar.openhuts.POJOs.HutList;
import com.omar.openhuts.R;
import com.omar.openhuts.Tools.HutAdapter;
import com.omar.openhuts.Tools.ListAdapter;
import com.omar.openhuts.Tools.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.omar.openhuts.R.id.list;

public class Favorites extends DefaultActivity {
	static ListView lv;
	private static HutAdapter adapter;
	ArrayList<Hut> huts;
	static ArrayList<Hut> hutsList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// Request
		int list = getIntent().getExtras().getInt("list");
		Request r = new Request(this);
		r.hutsList(list,this);

		Button btn = findViewById(R.id.edit);
		btn.setVisibility (View.GONE);
		btn.setEnabled(false);

		// Sets huts from preferences
		String lists = settings.getString("hutsList", "");
		JSONObject jObject;
		JSONArray jArray;
		ArrayList<Hut> hutList = null;
		try {
			jObject = new JSONObject(lists);
			jArray = jObject.getJSONArray("results");
			hutList = Request.hutsArrayToList(jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		setHuts(hutList, this);
	}

	public static void setHuts(ArrayList<Hut> huts, Context ctx){
		if (huts != null) {
			adapter = new HutAdapter(ctx, huts);
			final Activity a = (Activity) ctx;
			lv = a.findViewById(list);
			lv.setAdapter(adapter);

			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					int id_hut = hutsList.get(position).getId();
					a.startActivity(new Intent(a, HutPage.class)
							.putExtra("hut", id_hut));
				}
			});
		}
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

		Lists.add(new HutList("Favorites", 5, 1));
		Lists.add(new HutList("List 1", 3, 2));
		Lists.add(new HutList("List 2", 6, 3));
		Lists.add(new HutList("List 3", 7, 4));
		Lists.add(new HutList("List 4", 1, 5));

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
