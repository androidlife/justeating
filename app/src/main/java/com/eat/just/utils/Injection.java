package com.eat.just.utils;

import android.content.Context;
import android.location.Geocoder;

import com.eat.just.network.provider.retrofit.ApiManager;
import com.eat.just.screen.restaurants.ListContract;
import com.eat.just.screen.restaurants.ListModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

/**
 */

public class Injection {

    public static ListContract.Model provideListModel() {
        return new ListModel(ApiManager.getApiService());
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    public static Geocoder getGeoCoder(Context context) {
        return new Geocoder(context, Locale.getDefault());
    }
}
