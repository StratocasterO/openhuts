package com.omar.openhuts.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.omar.openhuts.Activities.DefaultActivity;
import com.omar.openhuts.POJOs.Hut;
import com.omar.openhuts.R;
import com.omar.openhuts.Tools.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class HutPage extends DefaultActivity {
	List<Hut> hutList;
	Hut hut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hut_page);

		// Gets the id from intent extras
		int id = getIntent().getExtras().getInt("hut");

		// Gets the huts from preferences
		String huts = settings.getString("huts","");
		JSONObject jObject;
		JSONArray jArray;
		try {
			jObject = new JSONObject(huts);
			jArray = jObject.getJSONArray("results");
			hutList = Request.hutsArrayToList(jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Iterator<Hut> iterator = hutList.iterator();
		while(iterator.hasNext()) {
			Hut item = iterator.next();
			if (item.getId()==id) hut = item;
		}

		TextView name = findViewById(R.id.name);
		name.setText(hut.getName());

		TextView desc = findViewById(R.id.description);
		desc.setText(hut.getDescription());

		RatingBar rb = findViewById(R.id.rating);
		rb.setRating(hut.getRating());

		TextView loc = findViewById(R.id.location);
		loc.setText(hut.getLocation().toString());
	}

	public void fav(View v){
		ImageView iv = findViewById(R.id.fav);
		if (iv.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.cor_empty).getConstantState()){
			iv.setImageResource(R.drawable.cor_full);
		} else {
			iv.setImageResource(R.drawable.cor_empty);
		}
	}

	// TODO "wrong data" message form
}
