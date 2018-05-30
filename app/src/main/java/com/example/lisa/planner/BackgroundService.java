package com.example.lisa.planner;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BackgroundService extends Service {

    LocationManager locationManager;
    Context mContext;

    int REFRESH_RATE = 7;
    int MIN_DISTANCE = 0;
    int DISTANCE_FROM_TASK = 100; // in meters

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext=this;
        locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                1000 * REFRESH_RATE,
                MIN_DISTANCE, locationListenerGPS);
        return super.onStartCommand(intent, flags, startId);
    }

    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            List<PlaceDetails> placeDetails = AppExtension.getPdl();
            double dLatitude=location.getLatitude();
            double dLongitude=location.getLongitude();

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(mContext, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(dLatitude, dLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                String msg="Address: " + address;

                //Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
                Log.d("__________INFO___________", msg);
                for (PlaceDetails task:
                     placeDetails) {
                    float[] distance = new float[1];
                    Location.distanceBetween(dLatitude, dLongitude, task.getLatitude(), task.getLongitude(), distance);
                    if (distance[0] < DISTANCE_FROM_TASK) {
                        msg = "You are within range of " + task.getName();
                        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
                        Log.d("__________INFO___________", msg);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


    };
}

