package com.omar.openhuts.Tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.omar.openhuts.Activities.MainActivity;
import com.omar.openhuts.POJOs.Hut;
import com.omar.openhuts.POJOs.HutList;
import com.omar.openhuts.POJOs.User;
import com.omar.openhuts.R;

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
//				.baseUrl("http://pablomonteserin.com:12973/")
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

	public void lists(final Context ctx) {
		Retrofit builder = new Retrofit.Builder()
				.baseUrl("http://pablomonteserin.com:12973/")
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
						List<HutList> lista = listsArrayToList(jArray); // TODO this into Lists activity

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

	// TODO edit into huts of a list
	public void hutList(final Context ctx) {
		Retrofit builder = new Retrofit.Builder()
				.baseUrl("http://pablomonteserin.com:12973/")
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
						List<HutList> lista = listsArrayToList(jArray); // TODO this into Lists activity

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

		final Retrofit builder = new Retrofit.Builder()
				.baseUrl("http://pablomonteserin.com:12973/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		// TODO fix request
		api.login(user).enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				String respuesta = null;
				try {
					respuesta = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (respuesta.equals("logged")) {
					MainActivity.settings.edit().putBoolean("logged", true).apply();
					Log.d("server", "login successful");
					// TODO recieve user from database
				} else {
					Log.d("server", "login error");
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				// Oops
			}
		});
	}

	public void register(final Context ctx, User user) {
		this.ctx = ctx;

		Retrofit builder = new Retrofit.Builder()
				.baseUrl("http://pablomonteserin.com:12973/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		api.register(user).enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				String respuesta = null;
				try {
					respuesta = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (respuesta.equals("registered")) {

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

	public static ArrayList<HutList> listsArrayToList(JSONArray jArray) {
		ArrayList<HutList> lista = new ArrayList<>();

		// TODO listsArrayToList()
		for (int i = 0; i < jArray.length(); i++) {
			try {
				JSONObject object = jArray.getJSONObject(i);

				int id = object.getInt("id");
				int num = object.getInt("num");
				String name = object.getString("name");


				HutList list = new HutList(name, num, id);
				lista.add(list);
			} catch (JSONException e) {
				// Oops
			}
		}
		return lista;
	}
}