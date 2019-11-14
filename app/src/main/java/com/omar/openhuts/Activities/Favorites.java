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
import android.widget.TextView;

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
	ArrayList<Hut> hutList;
	static ArrayList<Hut> hutsList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		String name = getIntent().getExtras().getString("name");
		TextView tv = findViewById(R.id.title);
		tv.setText(name);

		// Request
		int list = getIntent().getExtras().getInt("list");
		Request r = new Request(this);
		r.hutsList(list,this);

		Button btn = findViewById(R.id.edit);
		btn.setVisibility (View.GONE);
		btn.setEnabled(false);

		// Sets huts from preferences
		String lists = settings.getString("hutList" + list, "");
		JSONObject jObject;
		JSONArray jArray;
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
}
