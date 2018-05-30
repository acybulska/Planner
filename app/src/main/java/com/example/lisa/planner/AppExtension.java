package com.example.lisa.planner;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AppExtension extends Application {

    private final static String saveFileName = "planner-tasks-list.txt";
    Context mContext;

    @Override
    public String toString() {
        String result = "";
        for(PlaceDetails place : pdl) {
            result += place.toString();
            result += "-";
        }
        return result;
    }

    private static ArrayList<PlaceDetails> pdl;
    //private List<PlaceDetails> pdl;

    public AppExtension() {

    }

    public void setPdl(List<PlaceDetails> pdl) {

    }

    public static List<PlaceDetails> getPdl() {
        if (pdl == null) {
            pdl = new ArrayList<>();
//            AppExtension temp = new AppExtension();
//            pdl = decodeString(temp.readFromFile());
        }
        return pdl;
    }

    public void addPdl(PlaceDetails pd)
    {
        pdl.add(pd);
    }

    public void saveToFile() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(saveFileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(this.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    private String readFromFile() {
        String result = "";

        try {
            InputStream inputStream = this.openFileInput(saveFileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return result;
    }

    private static ArrayList<PlaceDetails> decodeString (String data) {
        ArrayList<PlaceDetails> result = new ArrayList<>();
        String[] placeDetailsList = data.split("-");
        for (String placeDetails : placeDetailsList) {
            String[] details = placeDetails.split(",");
            PlaceDetails task = new PlaceDetails(details[0], details[1], /*details[2],*/ Double.parseDouble(details[3]), Double.parseDouble(details[4]));
            result.add(task);
        }
        return result;
    }
}
// TODO endgame transfer to database
