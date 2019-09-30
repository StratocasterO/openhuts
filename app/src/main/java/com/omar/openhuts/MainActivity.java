package com.omar.openhuts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
	View mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO Personalise permission request message
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		assert mapFragment != null;
		mapView = mapFragment.getView();
		mapFragment.getMapAsync(this);
	}

	public void menu(View v) {
		Log.d("click", "clicked on menu");
		startActivity(new Intent(this, MenuApp.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	public void search(View v) {
		Log.d("click", "clicked on search");
	}

	public void add(View v) {
		Log.d("click", "clicked on add");
		startActivity(new Intent(this, AddHut.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	// Huts hardcoded for testing
	public ArrayList<Hut> getHuts() {
		ArrayList<Hut> Huts = new ArrayList<Hut>();

		Huts.add(new Hut(1, "hut1 name", 4.5f, new LatLng(42.0, 1.0), "img1"));
		Huts.add(new Hut(2, "hut2 name", 4.0f, new LatLng(43.0, 1.0), "img2"));
		Huts.add(new Hut(3, "hut3 name", 2.5f, new LatLng(41.0, 1.0), "img3"));
		Huts.add(new Hut(4, "hut4 name", 1.5f, new LatLng(42.0, 2.0), "img4"));
		Huts.add(new Hut(5, "hut5 name", 5f, new LatLng(42.0, 0.0), "img5"));

		return Huts;
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		googleMap.setMyLocationEnabled(true);

		//  Positioning My Location button on bottom right
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

		// Adding markers for the huts
		Iterator<Hut> iterator = getHuts().iterator();

		while (iterator.hasNext()) {
			Hut hut = iterator.next();

			googleMap.addMarker(new MarkerOptions()
					.position(hut.getLocation())
					// TODO rating bar into InfoWindow: https://developers.google.com/maps/documentation/android-sdk/infowindows
					//.snippet("~ distance km \n" + hut.getRating().toString() + "(rating bar)")
					.title(hut.getName())
			);
		}

		googleMap.setOnMarkerClickListener(this);

		// When click on marker snippet
		googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				startActivity(new Intent(MainActivity.this, HutPage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			}
		});

		// Default location
		LatLng idle = new LatLng(41.731113, 1.822789);

		CameraPosition camPos = new CameraPosition.Builder()
				.target(idle)       // Centramos el mapa en Madrid
				.zoom(7.0f)         // Establecemos el zoom en 19
				.bearing(0)         // Establecemos la orientación con el norte arriba
				.tilt(0)            // Inclinación vertical
				.build();

		CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
		googleMap.moveCamera(camUpd);
	}

	// When click on map marker
	@Override
	public boolean onMarkerClick(final Marker marker) {
		Log.d("click", "clicked on " + marker.getTitle());

		return false;
	}

	@Override
	public void onBackPressed() {
		getIntent().putExtra("EXIT", true);
		//Display alert message when back button has been pressed
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);
		// Setting Dialog Title
		alertDialog.setTitle("Leaving application");
		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want to leave Open Huts?");
		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.logo);
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// closes the activity if it's loaded because closing the app
						if (getIntent().getBooleanExtra("EXIT", false)) {
							finish();
						}
					}
				});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});
		// Showing Alert Message
		alertDialog.show();
		return;
	}

	@Override
	public void overridePendingTransition(int enterAnim, int exitAnim) {
		super.overridePendingTransition(0, 0);
	}
}