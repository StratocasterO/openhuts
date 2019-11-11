package com.omar.openhuts.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.omar.openhuts.POJOs.HutList;
import com.omar.openhuts.R;
import com.omar.openhuts.Tools.ListAdapter;
import com.omar.openhuts.Tools.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.omar.openhuts.R.id.list;

public class Lists extends DefaultActivity {
	static ListView lv;
	boolean pressed = false;
	static ListAdapter adapter;
	ArrayList<HutList> lists;
	static ArrayList<HutList> listList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		TextView tv = findViewById(R.id.title);
		tv.setText("Saved huts");

		// Request
		Request r = new Request(this);
		r.lists(this);

		// Sets lists from preferences
		String lists = settings.getString("lists", "");
		JSONObject jObject;
		JSONArray jArray;

		try {
			jObject = new JSONObject(lists);
			jArray = jObject.getJSONArray("results");
			listList = Request.listsArrayToList(jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// setLists(listList, this);
	}

	public static void setLists(ArrayList<HutList> lists, Context ctx){
		adapter = new ListAdapter(ctx, lists,false);
		final Activity a = (Activity) ctx;
		lv = a.findViewById(list);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int id_list = listList.get(position).getId();
				a.startActivity(new Intent(a, Favorites.class)
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
}
