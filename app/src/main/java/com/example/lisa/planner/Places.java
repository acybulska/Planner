package com.example.lisa.planner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.location.places.ui.PlacePicker;

public class Places extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        //startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        /*gps = new GPSTracker(Today.this);

                        if(gps.canGetLocation()){

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            Toast.makeText(getApplicationContext(),
                                    "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            gps.showSettingsAlert();
                        }*/
    }
}
