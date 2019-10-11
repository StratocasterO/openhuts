package com.omar.openhuts;

import android.Manifest;
import android.content.Context;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.omar.openhuts.MainActivity.googleMap;
import static com.omar.openhuts.MainActivity.settings;

public class Request {
	private Context ctx;
	public static List<Hut> lista = new ArrayList<>();

	public void hutsMapa(final Context ctx) {
		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetAPI apiGET = builder.create(GetAPI.class);

		apiGET.GET().enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> res) {
				try {
					String response = res.body().string();
					Log.d("server", "Datos recibidos");

					JSONObject jObject;
					try {
						jObject = new JSONObject(response);
						JSONArray jArray = jObject.getJSONArray("results");
						List<Hut> lista = fromArrayToList(jArray);

						// Saves data to preferences
						MainActivity.settings.edit().putString("huts", response).apply();

						// Set markers on the map
						MainActivity.markers(lista);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Toast.makeText(ctx, R.string.server_error, Toast.LENGTH_SHORT).show();
				t.printStackTrace();
			}
		});
	}

	public void postReq(final Context ctx, View v) {
		this.ctx = ctx;

		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		PostAPI apiPOST = builder.create(PostAPI.class);

		apiPOST.POST("Pablo", "Monteserrín").enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {

			}
		});
	}

	public static List<Hut> fromArrayToList(JSONArray jArray) {
		List<Hut> lista = new ArrayList<>();
		for (int i = 0; i < jArray.length(); i++) {
			try {
				JSONObject object = jArray.getJSONObject(i);
				// Pulling items from the array
				int id = object.getInt("id");
				String name = object.getString("name");
				String desc = object.getString("description");
				float rating = (float) object.getDouble("rating");
				LatLng location = new LatLng(object.getDouble("lat"),object.getDouble("lon"));
				int temp = object.getInt("temp");
				int wind = object.getInt("wind");
				int rain = object.getInt("rain");
				String img = object.getString("img");

				Hut hut = new Hut(id, name, desc, rating, location, temp, wind, rain,img);
				lista.add(hut);
			} catch (JSONException e) {
				// Oops
			}
		}
		return lista;
	}
	// TODO establish list of requests: login, register, map loading, hut page, profile page, new hut
}