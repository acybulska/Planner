package com.example.lisa.planner;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Places extends AppCompatActivity {

    FloatingActionButton addLocationBtn;
    LinearLayout linearLayout;

    GPSTracker gps;
    List<PlaceDetails> placeDetailsList;

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = "PlacesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        addLocationBtn = (FloatingActionButton)findViewById(R.id.addLocationFab);
        linearLayout = (LinearLayout)findViewById(R.id.placesList);

        gps = new GPSTracker(Places.this);
        placeDetailsList= new List<PlaceDetails>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<PlaceDetails> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(PlaceDetails placeDetails) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends PlaceDetails> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends PlaceDetails> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public PlaceDetails get(int index) {
                return null;
            }

            @Override
            public PlaceDetails set(int index, PlaceDetails element) {
                return null;
            }

            @Override
            public void add(int index, PlaceDetails element) {

            }

            @Override
            public PlaceDetails remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<PlaceDetails> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<PlaceDetails> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<PlaceDetails> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

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
        placeDetailsList.add(placeDetails);
        TextView tv = new TextView(getApplicationContext());
        tv.setText(placeDetails.getName());
        tv.setText(placeDetails.getAddress());
        // TODO create the delete function
        // TODO recycler view + card view
        linearLayout.addView(tv);
        ((AppExtension) this.getApplication()).setPdl(placeDetailsList);
    }
}
