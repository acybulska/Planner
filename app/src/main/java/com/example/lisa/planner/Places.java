package com.example.lisa.planner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

public class Places extends AppCompatActivity {

    FloatingActionButton addLocationBtn;
    LinearLayout linearLayout;

    GPSTracker gps;
    PlaceDetails[] pdl = new PlaceDetails[]{};

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = "PlacesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        addLocationBtn = (FloatingActionButton)findViewById(R.id.addLocationFab);
        linearLayout = (LinearLayout)findViewById(R.id.placesList);

        final List<PlaceDetails> pdl = ((AppExtension) this.getApplication()).getPdl();
        for(int i=0;i<pdl.size();i++)
        {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(pdl.get(i).getName());
            tv.setText(pdl.get(i).getAddress());
            // TODO create the delete function
            // TODO recycler view + card view
            linearLayout.addView(tv);
        }

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
                    }
                    catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place newPlace = PlacePicker.getPlace(this, data);
                addNewPlace(newPlace);
            }
        }
    }

    protected void addNewPlace(Place newPlace)
    {
        PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.setName(newPlace.getName().toString());
        placeDetails.setAddress(newPlace.getAddress().toString());
        placeDetails.setLatitude(newPlace.getLatLng().latitude);
        placeDetails.setLongitude(newPlace.getLatLng().longitude);
        TextView tv = new TextView(getApplicationContext());
        tv.setText(placeDetails.getName());
        tv.setText(placeDetails.getAddress());
        // TODO create the delete function
        // TODO recycler view + card view
        linearLayout.addView(tv);
        ((AppExtension) this.getApplication()).addPdl(placeDetails);
    }
}
