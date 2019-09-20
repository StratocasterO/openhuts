package com.omar.openhuts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.omar.openhuts.R;

import java.util.ArrayList;
import java.util.Iterator;

import static com.omar.openhuts.R.layout.activity_main;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    View mapView;

    // TODO presentation image before main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    public void menu(View v) {
        Log.d("click", "clicked on menu");
        startActivity(new Intent(this, MenuApp.class));
    }

    public void search(View v) {
        Log.d("click", "clicked on search");
    }

    public void add(View v) {
        Log.d("click", "clicked on add");
    }

    // Huts hardcoded for testing
    public ArrayList<Hut> getHuts() {
        ArrayList<Hut> Huts = new ArrayList<Hut>();

        LatLng loc = new LatLng(42, 1);
        Huts.add(new Hut(1, "hut1 name", 4.5f, new LatLng(42.0,1.0), "img1"));
        Huts.add(new Hut(2, "hut2 name", 4.0f, new LatLng(43.0,1.0), "img2"));
        Huts.add(new Hut(3, "hut3 name", 2.5f, new LatLng(41.0,1.0), "img3"));
        Huts.add(new Hut(4, "hut4 name", 1.5f, new LatLng(42.0,2.0), "img4"));
        Huts.add(new Hut(5, "hut5 name", 5f, new LatLng(42.0,0.0), "img5"));

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
        Iterator <Hut>iterator = getHuts().iterator();

        while(iterator.hasNext()){
            Hut hut = iterator.next();

            googleMap.addMarker(new MarkerOptions()
                    .position(hut.getLocation())
                    .snippet("~ distance km \n" + hut.getRating().toString() + "(rating bar)")
                    .title(hut.getName()));
        }
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
}