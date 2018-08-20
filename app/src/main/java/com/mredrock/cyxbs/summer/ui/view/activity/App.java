package com.mredrock.cyxbs.summer.ui.view.activity;

import android.app.Application;
import android.content.Context;

import com.mredrock.cyxbs.summer.utils.SPHelper;

public class App extends Application {
    private static Context appContext;
    private static SPHelper spHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        spHelper = new SPHelper(appContext,"Default");
    }

    public static Context getContext(){return appContext;}

    public static SPHelper spHelper(){return spHelper;}
}
