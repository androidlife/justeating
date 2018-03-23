package com.eat.just;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 */

public class MainApplication extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
