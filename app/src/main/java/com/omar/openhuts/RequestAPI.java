package com.omar.openhuts;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestAPI {
	// Fetch huts request
	@GET("huts/fetch")
	Call<ResponseBody> fetchHuts();

	// Fetch lists request
	@GET("lists/fetch")
	Call<ResponseBody> fetchLists();

	// Fetch huts in list request
	@GET("lists/fetchHuts")
	Call<ResponseBody> fetchHutList();

	// Login request
	// @Headers({"Accept: application/json"}) TODO post request (?)
	@Headers({"Accept: text/html"})
	@POST("users/login")
	Call<ResponseBody> login(@Body User user);

	// Register request
	// @Headers({"Accept: application/json"}) TODO post request (?)
	@Headers({"Accept: text/html"})
	@POST("users/register")
	Call<ResponseBody> register(@Body User user);

//  https://stackoverflow.com/questions/19796235/post-with-android-retrofit <- Example of POST
//	@FormUrlEncoded
//	@POST("/login")
//	void login(@Field("username") String username, @Field("password") String password, Callback<UserResponse> callback);
}

