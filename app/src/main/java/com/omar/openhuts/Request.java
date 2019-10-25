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
						List<Hut> lista = hutsArrayToList(jArray);

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

	public void listas(final Context ctx) {
		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		api.fetchLists().enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> res) {
				try {
					String response = res.body().string();
					Log.d("server", "Datos recibidos");

					JSONObject jObject;
					try {
						jObject = new JSONObject(response);
						JSONArray jArray = jObject.getJSONArray("results");
						List<List> lista = listsArrayToList(jArray);

						// Saves data to preferences
						MainActivity.settings.edit().putString("lists", response).apply();

						// TODO write lists onto Lists activity
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

		api.fetchHutList().enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> res) {
				try {
					String response = res.body().string();
					Log.d("server", "Datos recibidos");

					JSONObject jObject;
					try {
						jObject = new JSONObject(response);
						JSONArray jArray = jObject.getJSONArray("results");
						List<Hut> lista = hutsArrayToList(jArray);

						// Saves data to preferences
						MainActivity.settings.edit().putString("hutsInLists", response).apply();

						// TODO write huts onto Favorites activity
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

		// TODO fix request
		api.login(user).enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				if (response.toString() == "logged") {
					MainActivity.settings.edit().putBoolean("logged", true).apply();
				} else {
					Log.d("server", "login error");
				}
				boolean log = MainActivity.settings.getBoolean("logged", false);

				Toast.makeText(ctx, "logueado = " + log , Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				// Oops
			}
		});

//      https://stackoverflow.com/questions/19796235/post-with-android-retrofit <- Example of POST
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

	public void register(final Context ctx, User user) {
		this.ctx = ctx;

		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		api.register(user).enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				if (response.toString() == "registered") {

				} else {
					Log.d("server", "register error");
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				// Oops
			}
		});
	}

	public static List<Hut> hutsArrayToList(JSONArray jArray) {
		List<Hut> lista = new ArrayList<>();
		for (int i = 0; i < jArray.length(); i++) {
			try {
				JSONObject object = jArray.getJSONObject(i);
				// Pulling items from the array
				int id = object.getInt("id");
				String name = object.getString("name");
				String desc = object.getString("description");
				float rating = (float) object.getDouble("rating");
				LatLng location = new LatLng(object.getDouble("lat"), object.getDouble("lon"));
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

	public static List<List> listsArrayToList(JSONArray jArray) {
		List<List> lista = new ArrayList<>();

		// TODO listsArrayToList()

		return lista;
	}

	// TODO establish list of requests: hut page, profile page, new hut
}