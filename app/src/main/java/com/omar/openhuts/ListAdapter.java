package com.omar.openhuts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends android.widget.BaseAdapter {
	private ArrayList<HutList> Lists;
	private Context ctx;
	private boolean btn;

	ListAdapter(Context ctx, ArrayList<HutList> Lists, boolean btn) {
		super();
		this.ctx = ctx;
		this.Lists = Lists;
		this.btn = btn;
	}

	@Override
	public int getCount() {
		return Lists.size();
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
		tv.setText(Lists.get(index).getName());

		// Image in photo:
		ImageView iv = row.findViewById(R.id.photo);
		iv.setImageDrawable(iv.getResources().getDrawable(R.drawable.hut));

		// Number of elements inside
		TextView tv2 = row.findViewById(R.id.inside);
		String text = Lists.size() + " huts";
		tv2.setText(text);

		// Enables/disables buttons
		Button button = row.findViewById(R.id.delete);
		if(btn){
			button.setVisibility(View.VISIBLE);
			button.setEnabled(true);
		} else {
			button.setVisibility(View.GONE);
			button.setEnabled(false);
		}

		return row;
	}
}