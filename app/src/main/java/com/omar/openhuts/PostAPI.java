package com.omar.openhuts;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostAPI {
	//Las cabeceras deben estar en consonancia con el tipo de dato que vamos a recuperar u obtendremos un error 406
	//  @Headers({"Accept: application/json"})
	@Headers({"Accept: text/html"})
	@FormUrlEncoded
	@POST("curso/php/res/peticion-post-android-2.php/")  // here the URL of the request
	Call<ResponseBody> POST(@SafeParcelable.Field(id = 1, defaultValue = "nombre") String nombre, @SafeParcelable.Field(id = 1, defaultValue = "apellidos") String apellidos);
}
