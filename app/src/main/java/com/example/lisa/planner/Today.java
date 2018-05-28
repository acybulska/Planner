package com.example.lisa.planner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class Today extends AppCompatActivity {

    FloatingActionButton addCheckboxBtn;
    FloatingActionButton checkLocationBtn;
    EditText todayDate;
    EditText newTask;
    int fineLocPerm;
    int coarseLocPerm;
    int internetPerm;
    private static final int RECORD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        todayDate = (EditText)findViewById(R.id.todayDate);
        addCheckboxBtn = (FloatingActionButton)findViewById(R.id.addCheckboxFab);
        checkLocationBtn = (FloatingActionButton)findViewById(R.id.checkLocationFab);
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.dayLinLay);
        fineLocPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        coarseLocPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        internetPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        final List<PlaceDetails> pdl = ((AppExtension) this.getApplication()).getPdl();

        if (fineLocPerm != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RECORD_REQUEST_CODE);
        }
        if (coarseLocPerm != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RECORD_REQUEST_CODE);
        }
        if (internetPerm != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RECORD_REQUEST_CODE);
        }

        addCheckboxBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater
                                = (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.popup, null);
                        final PopupWindow popupWindow = new PopupWindow(
                                popupView,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        popupWindow.setFocusable(true);
                        newTask = (EditText) popupView.findViewById(R.id.newTask);
                        Button cancelBtn = (Button) popupView.findViewById(R.id.cancelBtn);
                        Button addBtn = (Button) popupView.findViewById(R.id.addBtn);
                        ScrollView pickPlace = (ScrollView) popupView.findViewById(R.id.choosePlace);
                        LinearLayout placesLin = (LinearLayout) popupView.findViewById(R.id.placesLin);

                        for(int i=0;i<pdl.size();i++)
                        {
                            TextView tv = new TextView(getApplicationContext());
                            tv.setText(pdl.get(i).getName());
                            placesLin.addView(tv);
                        }

                        cancelBtn.setOnClickListener(new Button.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });

                        addBtn.setOnClickListener(new Button.OnClickListener(){

                            @Override
                            public void onClick(View v) {

                                popupWindow.dismiss();

                                CheckBox cb = new CheckBox(getApplicationContext());
                                cb.setText(newTask.getText());
                                linearLayout.addView(cb);
                            }
                        });
                        popupWindow.showAsDropDown(todayDate,0,0);
                    }
                }
        );

        checkLocationBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Today.this,Places.class);
                        startActivity(intent);

                    }
                }
        );
    }
}
