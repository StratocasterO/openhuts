package com.omar.openhuts;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request {
	private Context ctx;
	public static List<Hut> lista = new ArrayList<>();

	public void hutsMapa(final Context ctx) {
		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		api.fetchHuts().enqueue(new Callback<ResponseBody>() {

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

	public void login(final Context ctx, User user) {
		this.ctx = ctx;

		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		api.login(user).enqueue(new Callback<ResponseBody>() { // TODO fix request
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				if (response.toString() == "logged") {
					MainActivity.settings.edit().putBoolean("logged", true).apply();
				} else {
					Log.d("server","login error");
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				// Oops
			}
		});

//		api.login(user, pass, new Callback<User>() {
//					@Override
//					public void failure(final RetrofitError error) {
//						android.util.Log.i("example", "Error, body: " + error.getBody().toString());
//					}
//					@Override
//					public void success(User user, Response response) {
//						// Do something with the User object returned
//					}
//				}
//		);
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
				String url = object.getString("url");

				Hut hut = new Hut(id, name, desc, rating, location, temp, wind, rain, img, url);
				lista.add(hut);
			} catch (JSONException e) {
				// Oops
			}
		}
		return lista;
	}
	// TODO establish list of requests: login, register, map loading, hut page, profile page, new hut
}