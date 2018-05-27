package com.example.lisa.planner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Places extends AppCompatActivity {

    FloatingActionButton addLocationBtn;
    GPSTracker gps;
    Place newPlace;

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = "PlacesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        addLocationBtn = (FloatingActionButton)findViewById(R.id.addLocationFab);

        gps = new GPSTracker(Places.this);

        addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();


                    LatLngBounds latLngBounds = new LatLngBounds(new LatLng(latitude, longitude),
                            new LatLng(latitude, longitude));
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    builder.setLatLngBounds(latLngBounds);

                    try {
                        Intent newPlacePicker = builder.build(Places.this);
                        startActivityForResult(newPlacePicker, PLACE_PICKER_REQUEST);
                        newPlace = PlacePicker.getPlace(newPlacePicker, Places.this);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.getStackTrace().toString());
                    }
                }
                else {
                    gps.showSettingsAlert();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
