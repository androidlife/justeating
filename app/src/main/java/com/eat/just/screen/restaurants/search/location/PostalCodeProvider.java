package com.eat.just.screen.restaurants.search.location;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;

import com.eat.just.model.Error;
import com.google.android.gms.location.FusedLocationProviderClient;

/**
 */

public class PostalCodeProvider implements LocationContract.PostCodeProvider {

    private LocationContract.PostCodeFromLocation postCodeFromLocation;
    private LocationContract.LocationProvider locationProvider;

    private boolean isCancelled = false;

    public PostalCodeProvider(FusedLocationProviderClient fusedLocationProviderClient,
                              Geocoder geocoder) {
        postCodeFromLocation = new LocationPostCode(geocoder);
        locationProvider = new LastLocationRetriever(fusedLocationProviderClient);
    }

    @Override
    public void cancel(boolean cancel) {
        isCancelled = cancel;
        postCodeFromLocation.cancel(cancel);
        locationProvider.cancel(cancel);
    }

    @Override
    public void fetchPostCode(final LocationContract.OnPostCodeFetchListener onPostCodeFetchListener) {
        locationProvider.fetchLocation(new LocationContract.OnLocationFetchListener() {
            @Override
            public void onLocationFetched(Location location) {
                retrievePostalCodeFromLocation(location, onPostCodeFetchListener);
            }

            @Override
            public void onLocationFetchError(Error error) {
                onFailure(error, onPostCodeFetchListener);
            }
        });
    }


    private void retrievePostalCodeFromLocation(Location location,
                                                final LocationContract.OnPostCodeFetchListener onPostCodeFetchListener) {
        postCodeFromLocation.fetchPostCodeFromLocation(location.getLatitude(), location.getLongitude(), new LocationContract.OnPostCodeFetchListener() {
            @Override
            public void onPostCodeFetched(String postCode) {
                onSuccess(postCode, onPostCodeFetchListener);
            }

            @Override
            public void onPostCodeFetchError(Error error) {
                onFailure(error, onPostCodeFetchListener);
            }
        });
    }


    private void onSuccess(String postCode, LocationContract.OnPostCodeFetchListener onPostCodeFetchListener) {
        if (isCancelled)
            return;
        onPostCodeFetchListener.onPostCodeFetched(postCode);
    }

    private void onFailure(Error error, LocationContract.OnPostCodeFetchListener onPostCodeFetchListener) {
        if (isCancelled)
            return;
        onPostCodeFetchListener.onPostCodeFetchError(error);

    }


}
