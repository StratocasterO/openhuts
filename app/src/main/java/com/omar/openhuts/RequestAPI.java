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
	Call<ResponseBody> fetchHuts();

	// Login request
	// Las cabeceras deben estar en consonancia con el tipo de dato que vamos a recuperar u obtendremos un error 406
	// @Headers({"Accept: application/json"}) TODO post request (?)
	@Headers({"Accept: text/html"})
	@FormUrlEncoded
	@POST("users/login")  // here the URL of the request
	Call<ResponseBody> login(@SafeParcelable.Field(id = 1, defaultValue = "user") String user, @SafeParcelable.Field(id = 1, defaultValue = "pass") String pass);

//  https://stackoverflow.com/questions/19796235/post-with-android-retrofit
//	@FormUrlEncoded
//	@POST("/login")
//	void login(@Field("username") String username, @Field("password") String password, Callback<UserResponse> callback);
}

