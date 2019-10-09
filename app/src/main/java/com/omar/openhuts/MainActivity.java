package com.omar.openhuts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
	View mapView;
	GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Makes image round #TODO icons with background for rounded button
//		ImageView iv = findViewById(R.id.add);
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add);
//		RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//		circularBitmapDrawable.setCircular(true);
//		iv.setImageDrawable(circularBitmapDrawable);

		hutsMapa();

		// Adds map
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		assert mapFragment != null;
		mapView = mapFragment.getView();
		mapFragment.getMapAsync(this);

		// Preferences for one time tasks:
		final String prefs = "MyPrefsFile";

		SharedPreferences settings = getSharedPreferences(prefs, 0);

		if (settings.getBoolean("my_first_time", true)) {
			// Welcome message:
			// TODO https://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MainActivity.this);
			// Setting Dialog Title
			alertDialog.setTitle("Welcome to Open Huts");
			// Setting Dialog Message
			alertDialog.setMessage("Open Huts is a cool application and here's why.");
			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.logo);
			// Setting Positive "Yes" Button
			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("Start",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to invoke NO event
							dialog.cancel();
						}
					});
			// Showing Alert Message
			alertDialog.show();

			settings.edit().putBoolean("my_first_time", false).apply();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 1: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// permission granted
					setupActivity();
					// googleMap.setMyLocationEnabled(true);  TODO enable My location if permission is granted (conditional in onMapReady?)
				} else {
					// permission denied
					setupActivity();
				}
				return;
			}
			// other "cases" if the app needs more permissions
		}
	}

	public void setupActivity() {
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		assert mapFragment != null;
		mapView = mapFragment.getView();
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(final GoogleMap googleMap) {

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO Personalise permission request message (lightbox)

			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
		} else {
			googleMap.setMyLocationEnabled(true);
		}

		// Positioning My Location button on bottom right
		if (mapView != null &&
				mapView.findViewById(Integer.parseInt("1")) != null) {
			// Get the button view
			View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
			// and next place it, on bottom right (as Google Maps app)
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
					locationButton.getLayoutParams();
			// position on right bottom
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			layoutParams.setMargins(0, 0, 12, 24);
		}

		// When My Location Button pressed
		googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
			@Override
			public boolean onMyLocationButtonClick() {
				Log.d("click", "clicked on my location");
				// TODO first click without permission -> request permission

				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();

				@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
				if (location != null)
				{
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
							.zoom(10)                   // Sets the zoom
							.bearing(0)                 // Sets the orientation of the camera to north
							.tilt(0)                    // Sets the tilt of the camera to 90 degrees
							.build();                   // Creates a CameraPosition from the builder
					googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				}

				return true;
			}
		});

		// Adding markers for the huts
		//for (Hut hut : getHuts())
//		for (Hut hut : lista) //TODO wait for response from the server
//			googleMap.addMarker(new MarkerOptions()
//					.position(hut.getLocation())
//					// TODO rating bar into InfoWindow: https://developers.google.com/maps/documentation/android-sdk/infowindows
//					//.snippet("~ distance km \n" + hut.getRating().toString() + "(rating bar)")
//					.title(hut.getName())
//			);

		// When click on map marker
		googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				Log.d("click", "clicked on " + marker.getTitle());
				marker.showInfoWindow();
				return true;
			}
		});

		// When click on marker snippet
		googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				startActivity(new Intent(MainActivity.this, HutPage.class));
			}
		});

		// Default location
		LatLng idle = new LatLng(41.731113, 1.822789);

		CameraPosition camPos = new CameraPosition.Builder()
				.target(idle)       // Centramos el mapa
				.zoom(7.0f)         // Establecemos el zoom en 19
				.bearing(0)         // Establecemos la orientación con el norte arriba
				.tilt(0)            // Inclinación vertical
				.build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
		googleMap.animateCamera(camUpd);
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//
//		if(googleMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
//			googleMap.clear();
//
//			// Adding markers for the huts
//			//for (Hut hut : getHuts())
//			for (Hut hut : r.lista) //TODO wait for response from the server
//				googleMap.addMarker(new MarkerOptions()
//						.position(hut.getLocation())
//						// TODO rating bar into InfoWindow: https://developers.google.com/maps/documentation/android-sdk/infowindows
//						//.snippet("~ distance km \n" + hut.getRating().toString() + "(rating bar)")
//						.title(hut.getName())
//				);
//		}
//	}

	@Override
	public void onBackPressed() {
		getIntent().putExtra("EXIT", true);

		//Display alert message when back button has been pressed
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);
		alertDialog.setTitle("Leaving application");
		alertDialog.setMessage("Are you sure you want to leave Open Huts?");
		alertDialog.setIcon(R.drawable.logo);
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// closes the activity if it's loaded because closing the app
						if (getIntent().getBooleanExtra("EXIT", false)) {
							finish();
						}
					}
				});
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}

	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class));
	}

	public void search(View v) {
		Log.d("click", "clicked on search");
	}

	public void add(View v) {
		// TODO first time click without login -> login/register lightbox

		Log.d("click", "clicked on add");
		startActivity(new Intent(this, AddHut.class));
	}

	public void lists(View v) {
		// TODO first time click without login -> login/register lightbox

		Log.d("click", "clicked on lists");
		startActivity(new Intent(this, Lists.class));
	}

	public void profile(View v) {
		// TODO first time click without login -> login/register lightbox

		Log.d("click", "clicked on profile");
		startActivity(new Intent(this, Profile.class));
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<>();

		Huts.add(new Hut(1, "hut1 name", "", 4.5f, new LatLng(42.0, 1.0), 1, 1, 1, "img1"));
		Huts.add(new Hut(2, "hut2 name", "", 4.0f, new LatLng(43.0, 1.0), 1, 1, 1, "img2"));
		Huts.add(new Hut(3, "hut3 name", "", 2.5f, new LatLng(41.0, 1.0), 1, 1, 1, "img3"));
		Huts.add(new Hut(4, "hut4 name", "", 1.5f, new LatLng(42.0, 2.0), 1, 1, 1, "img4"));
		Huts.add(new Hut(5, "hut5 name", "", 5f, new LatLng(42.0, 0.0), 1, 1, 1, "img5"));

		return Huts;
	}

	public void hutsMapa() {

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
					Toast.makeText(MainActivity.this, respuesta, Toast.LENGTH_LONG).show();

					JSONObject jObject;
					try {
						jObject = new JSONObject(respuesta);
						JSONArray jArray = jObject.getJSONArray("results");
						List<Hut> lista = fromArrayToList(jArray);
						Log.d("lista", ""+ lista);

						// Adding markers for the huts
//						googleMap.clear();

						for (Hut hut : lista) //TODO wait for response from the server
							googleMap.addMarker(new MarkerOptions()
									.position(hut.getLocation())
									// TODO rating bar into InfoWindow: https://developers.google.com/maps/documentation/android-sdk/infowindows
									//.snippet("~ distance km \n" + hut.getRating().toString() + "(rating bar)")
									.title(hut.getName())
							);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				Toast.makeText(MainActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
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
}