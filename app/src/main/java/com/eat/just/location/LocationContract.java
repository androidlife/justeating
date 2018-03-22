package com.eat.just.location;

import android.location.Location;

import com.eat.just.base.CancelCallback;
import com.eat.just.model.Error;

/**
 */

public interface LocationContract {
    interface LocationProvider extends CancelCallback {
        void fetchLocation(OnLocationFetchListener locationFetchListener);
    }

    interface PostCodeFromLocation extends CancelCallback {
        void fetchPostCodeFromLocation(double latitude, double longitude, OnPostCodeFetchListener postCodeFetchListener);
    }

    interface PostCodeProvider extends CancelCallback {
        void fetchPostCode(OnPostCodeFetchListener onPostCodeFetchListener);
    }

    interface OnLocationFetchListener {
        void onLocationFetched(Location location);

        void onLocationFetchError(Error error);
    }

    interface OnPostCodeFetchListener {
        void onPostCodeFetched(String postCode);

        void onPostCodeFetchError(Error error);
    }
}
