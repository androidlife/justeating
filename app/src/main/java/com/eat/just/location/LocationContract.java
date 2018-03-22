package com.eat.just.location;

import android.location.Location;

import com.eat.just.model.Error;

/**
 */

public interface LocationContract {
    interface LocationProvider {
        void cancel(boolean cancel);

        void fetchLocation(OnLocationFetchListener locationFetchListener);
    }

    interface OnLocationFetchListener {
        void onLocationFetched(Location location);

        void onLocationFetchError(Error error);
    }
}
