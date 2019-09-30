package com.omar.openhuts;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

	public void hacerPeticion(final Context ctx, View v) {
		this.ctx = ctx;

		Retrofit builder = new Retrofit.Builder()
				.baseUrl("") // TODO add server direction
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetAPI apiGET = builder.create(GetAPI.class);
		PostAPI apiPOST = builder.create(PostAPI.class);

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
						List<String> lista = fromArrayToList(jArray);
						//ListView lv = findViewById(R.id.personas);
						//lv.setAdapter(new ArrayAdapter(ctx, android.R.layout.simple_list_item_1, lista));
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
			}

			private List<String> fromArrayToList(JSONArray jArray) {
				List<String> lista = new ArrayList<String>();
				for (int i = 0; i < jArray.length(); i++) {
					try {
						JSONObject oneObject = jArray.getJSONObject(i);
						// Pulling items from the array
						String name = oneObject.getString("name");
						lista.add(name);
					} catch (JSONException e) {
						// Oops
					}
				}
				return lista;
			}
		});

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