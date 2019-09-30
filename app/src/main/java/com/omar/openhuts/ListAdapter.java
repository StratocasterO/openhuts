package com.omar.openhuts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends android.widget.BaseAdapter {
	ArrayList<Hut> Huts;
	Context ctx;

	public ListAdapter(Context ctx, ArrayList<Hut> Huts) {
		super();
		this.ctx = ctx;
		this.Huts = Huts;
	}

	@Override
	public int getCount() {
		return Huts.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(int index, View convertView, ViewGroup parent) {
		View row = View.inflate(ctx, R.layout.list, null);
		TextView tv = row.findViewById(R.id.name);
		tv.setText(Huts.get(index).getName());

		// Image in photo:
		ImageView iv = row.findViewById(R.id.photo);
		iv.setImageDrawable(iv.getResources().getDrawable(R.drawable.hut));

		// TODO conditionals for the followng two elements (list/favs):
		// Compute the distance from the user to the hut and show it in tv2:
		TextView tv2 = row.findViewById(R.id.distance);
		tv2.setText("~ " + "distance" +" km");

		// Number of elements inside
		TextView tv2 = row.findViewById(R.id.inside);
		tv2.setText(num + "huts");

		// Rating:
		RatingBar rb = row.findViewById(R.id.rating);
		rb.setRating(Huts.get(index).getRating());

		return row;
	}
}