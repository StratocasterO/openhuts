package com.omar.openhuts.Tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.omar.openhuts.Activities.Lists;
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
	private String urlAWS = "http://openhutsserver-env.ifamk2b2ph.eu-west-3.elasticbeanstalk.com/";
	private String urlPablo = "http://pablomonteserin.com:12973/";
	private String urlHeroku = "https://openhuts.herokuapp.com/";

	private Context ctx;
	public static List<Hut> lista = new ArrayList<>();

	public Request(Context ctx){
		this.ctx = ctx;
	}

	public void hutsMapa(final Context ctx) {
		Retrofit builder = new Retrofit.Builder()
				.baseUrl(urlAWS)
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
				.baseUrl(urlAWS)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

		api.fetchLists(MainActivity.user.getId()).enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> res) {
				try {
					String response = res.body().string();
					Log.d("server", "Datos recibidos");

					JSONObject jObject;
					try {
						jObject = new JSONObject(response);
						JSONArray jArray = jObject.getJSONArray("results"); // TODO has to manage different elements of the array separately
						ArrayList<HutList> lista = listsArrayToList(jArray);

						// Saves data to preferences
						MainActivity.settings.edit().putString("lists", response).apply();

						Lists.setLists(lista, ctx);
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
				.baseUrl(urlAWS)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		RequestAPI api = builder.create(RequestAPI.class);

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
						MainActivity.settings.edit().putString("list", response).apply(); // TODO save into list ID

						// TODO write huts onto Fav activity
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

	public void login(final Context ctx, final User user) {
		this.ctx = ctx;

		final Retrofit builder = new Retrofit.Builder()
				.baseUrl(urlAWS)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		final RequestAPI api = builder.create(RequestAPI.class);

		api.login(user).enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> res) {
				String response = null;
				String log;
				JSONObject user;
				JSONObject jObject;
				try {
					response = res.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.d("server", "Datos recibidos");

				try {
					jObject = new JSONObject(response);
					log = jObject.getString("log");
					user = new JSONObject(jObject.getString("user"));

					if (log.equals("logged")) {
						MainActivity.settings.edit().putBoolean("logged", true).apply();
						Log.d("server", "login successful");

						// Saves user to preferences
						MainActivity.settings.edit().putString("user", jObject.getString("user")).apply();
						MainActivity.user = userJsonToUser(user);
					} else {
						Log.d("server", "login error");
					}
				} catch (JSONException e) {
					e.printStackTrace();
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
				.baseUrl(urlAWS)
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

	public static User userJsonToUser(JSONObject user) {
		User userJson = new User(0,"","","","","","");

		try {
			userJson.setId(user.getInt("id"));
			userJson.setName(user.getString("name"));
			userJson.setEmail(user.getString("email"));
			userJson.setPass(user.getString("pass"));
			userJson.setDescription(user.getString("description"));
			userJson.setLocation(user.getString("location"));
			userJson.setImg(user.getString("img"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return userJson;
	}
}