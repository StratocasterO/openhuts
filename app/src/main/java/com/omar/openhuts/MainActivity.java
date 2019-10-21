package com.omar.openhuts;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

import java.util.List;

public class MainActivity extends DefaultActivity implements OnMapReadyCallback {
	static GoogleMap googleMap;
	static String prefs = "MyPrefsFile";
	static SharedPreferences settings;
	View mapView;

	public static void markers(List<Hut> lista) {
		// Adding markers for the huts
		if (lista != null) {
			for (Hut hut : lista) {
				Marker marker = googleMap.addMarker(new MarkerOptions()
						.position(hut.getLocation())
						// TODO rating bar into InfoWindow: https://developers.google.com/maps/documentation/android-sdk/infowindows
						//.snippet("~ distance km \n" + hut.getRating().toString() + "(rating bar)")
						.title(hut.getName())
				);
				marker.setTag(hut.getId());
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Makes image round #TODO icons with background for rounded button
		ImageView iv = findViewById(R.id.add);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_fons);
		RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
		circularBitmapDrawable.setCircular(true);
		iv.setImageDrawable(circularBitmapDrawable);

		// Adds map
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		assert mapFragment != null;
		mapView = mapFragment.getView();
		mapFragment.getMapAsync(this);

		// checks in preferences if it is the first time opening the app
		settings = getSharedPreferences(prefs, 0);

		if (settings.getBoolean("my_first_time", true)) {
			// Welcome message:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MainActivity.this);
			// Setting Dialog Title
			alertDialog.setTitle("Bienvenido a Open Huts");
			// Setting Dialog Message
			alertDialog.setMessage("Esta aplicación te permite registrar y consultar refugios de montaña.\n\nVamos a pedirte acceso a la localización de tu móvil. Si no la autorizas no funcionarán algunas características, pero podrás seguir usando la app.\n\nPuedes autorizarla más tarde en los ajustes del móvil Apps>Permisos>Localización");
			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.logo);
			// Setting Positive "Yes" Button
			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("Empezar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to invoke NO event
							dialog.cancel();
							requestPermission();
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
					googleMap.setMyLocationEnabled(true);
				} else {
					// permission denied
					setupActivity();
				}
				return;
			}
			// Other cases if the app needs more permissions
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
		MainActivity.googleMap = googleMap;

		// Makes request to the server
		Request r = new Request();
		r.hutsMapa(this);

		// Sets markers from preferences
		String huts = settings.getString("huts", "");
		JSONObject jObject;
		JSONArray jArray;
		List<Hut> hutList = null;
		try {
			jObject = new JSONObject(huts);
			jArray = jObject.getJSONArray("results");
			hutList = Request.fromArrayToList(jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		markers(hutList);

		requestPermission();

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
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();

				@SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
				if (location != null) {
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

		// When click on map marker
		googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				Log.d("click", "clicked on " + marker.getTitle());
				marker.showInfoWindow();
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
						.zoom(10)
						.build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				return true;
			}
		});

		// When click on marker snippet
		googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				startActivity(new Intent(MainActivity.this, HutPage.class).putExtra("hut", (int) marker.getTag()));
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

	private void requestPermission() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
		} else {
			googleMap.setMyLocationEnabled(true);
		}
	}

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

	public void search(View v) {
		Log.d("click", "clicked on search");
	}

	public void add(View v) {
		Log.d("click", "clicked on add");

		if (logged()) {
			startActivity(new Intent(this, AddHut.class));
		} else {
			LoginRegister();
		}
	}

	public void lists(View v) {
		Log.d("click", "clicked on lists");

		if (logged()) {
			startActivity(new Intent(this, Lists.class));
		} else {
			LoginRegister();
		}
	}

	public void profile(View v) {
		Log.d("click", "clicked on profile");

		if (logged()) {
			startActivity(new Intent(this, Profile.class));
		} else {
			LoginRegister();
		}
	}

	public void LoginRegister() {
		LayoutInflater inflater = this.getLayoutInflater();

		// Login dialog
		final AlertDialog.Builder login = new AlertDialog.Builder(MainActivity.this);
		login.setView(inflater.inflate(R.layout.dialog_login, null));
		login.setCancelable(false);
		login.setTitle("Log in");
		login.setIcon(R.drawable.logo);
		login.setPositiveButton("Log in",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface login, int which) {
						Log.d("click", "clicked on button login");
						EditText u = findViewById(R.id.email);
						EditText p = findViewById(R.id.password);
						String user = u.getText().toString();
						String pass = p.getText().toString();
						Request r = new Request();
						r.login(MainActivity.this, user, pass);
						login.cancel();
					}
				});
		login.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface login, int which) {
						login.cancel();
					}
				});

		// Register dialog
		final AlertDialog.Builder register = new AlertDialog.Builder(MainActivity.this);
		register.setView(inflater.inflate(R.layout.dialog_register, null));
		register.setCancelable(false);
		register.setTitle("Register");
		register.setIcon(R.drawable.logo);
		register.setPositiveButton("Register",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface register, int which) {
						Log.d("click", "clicked on button register");
						// TODO Request.register();
						register.cancel();
					}
				});
		register.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface register, int which) {
						register.cancel();
					}
				});

		// Login/register dialog
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
		alertDialog.setTitle("Login or register");
		alertDialog.setMessage("This feature requires login or registration");
		alertDialog.setIcon(R.drawable.logo);
		alertDialog.setPositiveButton("Log in",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						login.show();
					}
				});
		alertDialog.setNegativeButton("Register",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						register.show();
					}
				});
		alertDialog.show();
	}
}