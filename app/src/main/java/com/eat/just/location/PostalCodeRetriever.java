package com.eat.just.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.eat.just.model.Error;

import java.util.List;
import java.util.Locale;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 */

public class PostalCodeRetriever implements LocationContract.PostCodeProvider {

    Geocoder geocoder;
    private boolean isCancelled = false;

    public PostalCodeRetriever(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    @Override
    public void cancel(boolean cancel) {
        isCancelled = true;
    }

    @Override
    public void fetchPostCodeFrom(final double latitude, final double longitude, final LocationContract.OnPostCodeFetchListener postCodeFetchListener) {
        Maybe.fromCallable(() -> getPostCodeFrom(latitude, longitude))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> onSuccess(s, postCodeFetchListener),
                        e -> onFailure(postCodeFetchListener));
    }

    private void onSuccess(String postCode, LocationContract.OnPostCodeFetchListener postCodeFetchListener) {
        if (isCancelled)
            return;
        if (postCode == null) {
            onFailure(postCodeFetchListener);
        } else {
            postCodeFetchListener.onPostCodeFetched(postCode);
        }
    }

    private void onFailure(LocationContract.OnPostCodeFetchListener postCodeFetchListener) {
        if (isCancelled)
            return;
        postCodeFetchListener.onPostCodeFetchError(new Error(Error.Type.PostalCode));
    }


    private String getPostCodeFrom(double latitude, double longitude) {
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList == null || addressList.size() == 0) {
                Timber.e("Couldn't find any location for given latitude = %f, longitude = %f",
                        latitude, longitude);
                return null;
            }

            Address address = addressList.get(0);
            String postalCode = address.getPostalCode();
            if (address.getCountryName().equals("United Kingdom"))
                postalCode = postalCode.split(" ")[0];
            Timber.d("Postal code for given latitude = %f and longitude = %f is %s",
                    latitude, longitude, postalCode);
            return postalCode;
        } catch (Exception e) {
            Timber.e("Address fetch exception due to : %s", e.getMessage());
        }
        return null;
    }
}
