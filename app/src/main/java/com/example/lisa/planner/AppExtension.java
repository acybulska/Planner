package com.example.lisa.planner;

import android.app.Application;

import java.util.List;

public class AppExtension extends Application {
    private List<PlaceDetails> pdl;

    public List<PlaceDetails> getPdl() {
        return pdl;
    }

    public void setPdl(List<PlaceDetails> pdl) {
        this.pdl = pdl;
    }
}
