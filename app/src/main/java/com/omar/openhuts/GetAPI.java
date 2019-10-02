package com.omar.openhuts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAPI {
	@GET("huts/fetch/") // here the URL of the request
	Call<ResponseBody> GET();
}

