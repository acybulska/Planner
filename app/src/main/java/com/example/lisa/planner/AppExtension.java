package com.example.lisa.planner;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class AppExtension extends Application {

    private List<PlaceDetails> pdl;

    public AppExtension()
    {
        pdl= new ArrayList<>();
    }

    public void setPdl(List<PlaceDetails> pdl) {
        this.pdl = pdl;
    }

    public List<PlaceDetails> getPdl() {
        if (pdl == null) {
            pdl = new ArrayList<>();
        }
        return pdl;
    }

    public void addPdl(PlaceDetails pd)
    {
        pdl.add(pd);
    }
}
// TODO endgame transfer to database
