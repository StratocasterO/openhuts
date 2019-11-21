package com.omar.openhuts.Activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.omar.openhuts.POJOs.User;
import com.omar.openhuts.R;
import com.omar.openhuts.Tools.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.omar.openhuts.Tools.Request.userJsonToUser;

public class Profile extends DefaultActivity {
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		String userString = settings.getString("user", "{\n" +
				"        \"id\": 0,\n" +
				"        \"name\": \"\",\n" +
				"        \"email\": \"\",\n" +
				"        \"pass\": \"\",\n" +
				"        \"description\": \"\",\n" +
				"        \"location\": \"\",\n" +
				"        \"img\": \"\"\n" +
				"    }");
		JSONObject jObject;
		try {
			jObject = new JSONObject(userString);
			user = Request.userJsonToUser(jObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// User data
		TextView name = findViewById(R.id.name);
		name.setText(user.getName());

		TextView desc = findViewById(R.id.description);
		desc.setText(user.getDescription());

		TextView loc = findViewById(R.id.location);
		loc.setText(user.getLocation());

		// Image
		ImageView iv = findViewById(R.id.photo);

		// Makes image round
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hut);
		RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
		circularBitmapDrawable.setCircular(true);
		iv.setImageDrawable(circularBitmapDrawable);
	}

	public void logout(View v){
		Log.d("click", "clicked on logout");

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				this);
		alertDialog.setTitle("Logging out");
		alertDialog.setMessage("Are you sure you want to log out?");
		alertDialog.setIcon(R.drawable.logo);
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						settings.edit().putBoolean("logged", false).apply();
						settings.edit().putString("user","{\n" +
								"        \"id\": 0,\n" +
								"        \"name\": \"\",\n" +
								"        \"email\": \"\",\n" +
								"        \"pass\": \"\",\n" +
								"        \"description\": \"\",\n" +
								"        \"location\": \"\",\n" +
								"        \"img\": \"\"\n" +
								"    }").apply();
						finish();
						overridePendingTransition(0,0);
					}
				});
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();

	}

	public void settings(View v){
		Log.d("click", "clicked on settings");
		overridePendingTransition(0,0);
	}
}