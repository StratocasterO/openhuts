package com.omar.openhuts.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.omar.openhuts.POJOs.Hut;
import com.omar.openhuts.POJOs.User;
import com.omar.openhuts.R;
import com.omar.openhuts.Tools.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends DefaultActivity implements OnMapReadyCallback {
	static GoogleMap googleMap;
	static String prefs = "MyPrefsFile";
	public static SharedPreferences settings;
	public static User user = new User(0,"","","","","","");
	View mapView;
	private int hutId;
	static Bitmap smallMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set icons
		ImageButton ib = findViewById(R.id.map_button);
		ib.setImageResource(R.drawable.map);

		ImageButton ib2 = findViewById(R.id.lists);
		ib2.setImageResource(R.drawable.favorites_no);

		ImageButton ib3 = findViewById(R.id.profile);
		ib3.setImageResource(R.drawable.profile_no);

		// Adds map
		setupActivity();

		// Sets size of markers
		int height = 95;
		int width = 71;
		BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);
		Bitmap b = bitmapdraw.getBitmap();
		smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

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

		// Load user from preferences
		JSONObject jsonUser = null;
		try {
			jsonUser = new JSONObject(settings.getString("user","{\n" +
					"        \"id\": 0,\n" +
					"        \"name\": \"\",\n" +
					"        \"email\": \"\",\n" +
					"        \"pass\": \"\",\n" +
					"        \"description\": \"\",\n" +
					"        \"location\": \"\",\n" +
					"        \"img\": \"\"\n" +
					"    }"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Sets user
		user = Request.userJsonToUser(jsonUser);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
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

	public static void markers(List<Hut> lista) {
		// Adding markers for the huts

		if (lista != null) {
			for (Hut hut : lista) {
				Marker marker = googleMap.addMarker(new MarkerOptions()
						.position(hut.getLocation())
						.title(hut.getName())
						.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
				);
				marker.setTag(hut.getId());
			}
		}
	}

	@Override
	public void onMapReady(final GoogleMap googleMap) {
		MainActivity.googleMap = googleMap;

		// Makes request to the server
		Request r = new Request(this);
		r.hutsMapa(this);

		// Sets markers from preferences
		String huts = settings.getString("huts", "");
		JSONObject jObject;
		JSONArray jArray;
		List<Hut> hutList = null;
		try {
			jObject = new JSONObject(huts);
			jArray = jObject.getJSONArray("results");
			hutList = Request.hutsArrayToList(jArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		markers(hutList);

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
		final ConstraintLayout card = findViewById(R.id.hutCard);
		googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				Log.d("click", "clicked on " + marker.getTitle());
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
						.zoom(10)
						.build();
				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

				TextView name = findViewById(R.id.name_text);
				name.setText(marker.getTitle());

				TextView dist = findViewById(R.id.location);
				dist.setText("Location");

				RatingBar rating = findViewById(R.id.ratingBar);
				rating.setRating(3.5f); // TODO read real value

				ImageView img = findViewById(R.id.image);
				img.setImageResource(R.drawable.hut);

				hutId = (int) marker.getTag();

				card.setVisibility(View.VISIBLE);

				return true;
			}
		});

		// Closes card when click outside marker
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				card.setVisibility(View.INVISIBLE);
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

		// Sets up MyLocation
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			googleMap.setMyLocationEnabled(true);
		}
	}

	public void card(View v){
		startActivity(new Intent(MainActivity.this, HutPage.class).putExtra("hut", hutId));
		overridePendingTransition(0,0);
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

	@Override
	public void add(View v) {
		Log.d("click", "clicked on add");

		if (logged()) {
			startActivity(new Intent(this, AddHut.class));
			overridePendingTransition(0,0);
		} else {
			LoginRegister(1);
		}
	}

	@Override
	public void lists(View v) {
		Log.d("click", "clicked on lists");

		if (logged()) {
			startActivity(new Intent(this, Lists.class));
			overridePendingTransition(0,0);
		} else {
			LoginRegister(2);
		}
	}

	@Override
	public void profile(View v) {
		Log.d("click", "clicked on profile");

		if (logged()) {
			startActivity(new Intent(this, Profile.class));
			overridePendingTransition(0,0);
		} else {
			LoginRegister(3);
		}
	}

	public void LoginRegister(final int c) {
		LayoutInflater inflater = this.getLayoutInflater();

		// Login dialog
		final AlertDialog.Builder login = new AlertDialog.Builder(MainActivity.this);
		final View loginDialog = inflater.inflate(R.layout.dialog_login, null);
		login.setView(loginDialog);
		login.setCancelable(false);
		login.setTitle(R.string.login);
		login.setIcon(R.drawable.logo);
		login.setPositiveButton(R.string.login,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface login, int which) {
						Log.d("click", "clicked on button login");
						EditText u = loginDialog.findViewById(R.id.email);
						EditText p = loginDialog.findViewById(R.id.password);
						String us = u.getText().toString();
						String pa = p.getText().toString();

						if (us.equals("admin") && pa.equals("123456")){
							settings.edit().putBoolean("logged", true).apply();

						} else {
							user.setName(us);
							user.setPass(pa);
							Request r = new Request(MainActivity.this);
							r.login(MainActivity.this, user);
						}

						// Saves user to preferences
						settings.edit().putString("user", "{\n" +
								"        \"id\": " + user.getId() + ",\n" +
								"        \"name\": \"" + user.getName() + "\",\n" +
								"        \"email\": \"" + user.getEmail() + "\",\n" +
								"        \"pass\": \"" + user.getPass() + "\",\n" +
								"        \"description\": \"" + user.getDescription() + "\",\n" +
								"        \"location\": \"" + user.getLocation() + "\",\n" +
								"        \"img\": \"" + user.getImg() + "\"\n" +
								"    }").apply();

						//boolean log = MainActivity.settings.getBoolean("logged", false);
						// Toast.makeText(MainActivity.this, "logueado = " + log , Toast.LENGTH_SHORT).show();
						login.cancel();
						if (logged()) caso(c);
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
		final View registerDialog = inflater.inflate(R.layout.dialog_register, null);
		register.setView(registerDialog);
		register.setCancelable(false);
		register.setTitle(R.string.register);
		register.setIcon(R.drawable.logo);
		register.setPositiveButton(R.string.register,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface register, int which) {
						Log.d("click", "clicked on button register");
						EditText e = loginDialog.findViewById(R.id.email);
						EditText u = loginDialog.findViewById(R.id.user);
						EditText p = loginDialog.findViewById(R.id.password);
						EditText p2 = loginDialog.findViewById(R.id.repass);

						if (p == p2) {
							User user = new User(0, u.getText().toString(), e.getText().toString(), p.getText().toString(), "", "", "");
							Request r = new Request(MainActivity.this);
							r.register(MainActivity.this, user);
							register.cancel();
						} else {
							// Password not repeated correctly
							Toast.makeText(MainActivity.this, "Check your password", Toast.LENGTH_SHORT).show();
						}

						if (logged()) caso(c);
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

	private void caso(int c){
		switch (c) {
			case 1:
				startActivity(new Intent(this, AddHut.class));
				overridePendingTransition(0,0);
				break;
			case 2:
				startActivity(new Intent(this, Lists.class));
				overridePendingTransition(0,0);
				break;
			case 3:
				startActivity(new Intent(this, Profile.class));
				overridePendingTransition(0,0);
				break;
			default:
		}
	}
}