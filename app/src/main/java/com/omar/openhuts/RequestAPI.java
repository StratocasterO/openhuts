package com.omar.openhuts;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestAPI {
	// Fetch huts request
	@GET("huts/fetch")
	Call<ResponseBody> GET();

	// Login request
	// Las cabeceras deben estar en consonancia con el tipo de dato que vamos a recuperar u obtendremos un error 406
	// @Headers({"Accept: application/json"}) TODO post request (?)
	@Headers({"Accept: text/html"})
	@FormUrlEncoded
	@POST("curso/php/res/peticion-post-android-2.php/")  // here the URL of the request
	Call<ResponseBody> POST(@SafeParcelable.Field(id = 1, defaultValue = "user") String user, @SafeParcelable.Field(id = 1, defaultValue = "pass") String pass);
}

