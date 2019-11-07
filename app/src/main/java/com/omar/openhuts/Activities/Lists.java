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
import com.omar.openhuts.Tools.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

		//lists = getLists();

		// Request
		Request r = new Request();
		r.listas(this);

		// Sets lists from preferences
		String lists = settings.getString("lists", "");
		JSONObject jObject;
		JSONArray jArray;
		ArrayList<HutList> listList = null;
		try {
			jObject = new JSONObject(lists);
			jArray = jObject.getJSONArray("results");
			listList = Request.listsArrayToList(jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		adapter = new ListAdapter(this, listList,false);

		lv = findViewById(list);
		lv.setAdapter(adapter);

		final ArrayList<HutList> finalListList = listList;
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int id_list = finalListList.get(position).getId();
				startActivity(new Intent(Lists.this, Favorites.class)
						.putExtra("list", id_list));
			}
		});
	}

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

	// TODO delete list when pressing button (request)
	public void delete(View v) {
		View item = (View) v.getParent();
		int pos = lv.getPositionForView(item);
		Log.d("click", "clicked on delete of item " + pos);
	}

	// Lists hardcoded for testing
	public ArrayList<HutList> getLists() {
		ArrayList<HutList> Lists = new ArrayList<HutList>();

		Lists.add(new HutList("Favorites", 5, 1));
		Lists.add(new HutList("List 1", 5, 2));
		Lists.add(new HutList("List 2", 5, 3));
		Lists.add(new HutList("List 3", 5, 4));
		Lists.add(new HutList("List 4", 5, 5));

		return Lists;
	}
}
