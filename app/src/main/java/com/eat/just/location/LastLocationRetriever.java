package com.eat.just.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.eat.just.model.Error;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import timber.log.Timber;

/**
 */

public class LastLocationRetriever implements LocationContract.LocationProvider {

    private boolean isCancelled = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    public LastLocationRetriever(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void cancel(boolean cancel) {
        isCancelled = cancel;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void fetchLocation(final LocationContract.OnLocationFetchListener locationFetchListener) {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Location lastLocation = task.getResult();
                Timber.d("Last location : Latitude [ %f ], Longitude [ %f ]", lastLocation.getLatitude(),
                        lastLocation.getLongitude());
                onLocationFetchSuccess(lastLocation, locationFetchListener);
                return;
            }
            Timber.e("Error fetching location");
            onLocationFetchError(new Error(Error.Type.LocationNotAvailable), locationFetchListener);
        });
    }

    private void onLocationFetchSuccess(Location location, LocationContract.OnLocationFetchListener locationFetchListener) {
        if (isCancelled)
            return;
        locationFetchListener.onLocationFetched(location);

    }

    private void onLocationFetchError(Error error, LocationContract.OnLocationFetchListener locationFetchListener) {
        if (isCancelled)
            return;
        locationFetchListener.onLocationFetchError(error);

    }
}
