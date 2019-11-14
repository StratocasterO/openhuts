package com.omar.openhuts.Tools;

import com.omar.openhuts.POJOs.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {
	// Fetch huts request
	@GET("huts/fetch")
	Call<ResponseBody> fetchHuts();

	// Fetch lists request
	@GET("lists/fetch")
	Call<ResponseBody> fetchLists(@Query("id") Integer id);

	// Fetch huts in list request
	@GET("lists/fetchHuts")
	Call<ResponseBody> fetchHutList(@Query("id") Integer id);

	// Login request
	@Headers({"Accept: text/html"})
	@POST("users/login")
	Call<ResponseBody> login(@Body User user);

	// Register request
	@Headers({"Accept: text/html"})
	@POST("users/register")
	Call<ResponseBody> register(@Body User user);
}

