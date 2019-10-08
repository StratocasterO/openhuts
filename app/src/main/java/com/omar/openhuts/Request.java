package com.omar.openhuts;

import android.Manifest;
import android.content.Context;
import android.os.Debug;
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

public class Request {
	private Context ctx;
	public static List<Hut> lista = new ArrayList<>();

	public void hutsMapa(final Context ctx) {
		this.ctx = ctx;


		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(30, TimeUnit.MINUTES)
				.readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS)
				.build();


		Retrofit builder = new Retrofit.Builder()
				.baseUrl("https://openhuts.herokuapp.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(okHttpClient)
				.build();

		GetAPI apiGET = builder.create(GetAPI.class);

		apiGET.GET().enqueue(new Callback<ResponseBody>() {

			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				try {
					String respuesta = response.body().string();
					Log.d("RESPUESTA", respuesta);
					Toast.makeText(ctx, respuesta, Toast.LENGTH_LONG).show();

					JSONObject jObject;
					try {
						jObject = new JSONObject(respuesta);
						JSONArray jArray = jObject.getJSONArray("results");
						List<Hut> lista = fromArrayToList(jArray);
						Log.d("lista", ""+ lista);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Toast.makeText(ctx, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
				t.printStackTrace();
			}

			private List<Hut> fromArrayToList(JSONArray jArray) {
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

						Hut hut = new Hut(id, name, desc, rating,location,temp,wind, rain,"");
						lista.add(hut);
					} catch (JSONException e) {
						// Oops
					}
				}
				return lista;
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

	// TODO establish list of requests: login, register, map loading, hut page, profile page, new hut
}