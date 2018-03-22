package com.eat.just.screen.restaurants;

import com.eat.just.base.CancelCallback;
import com.eat.just.model.Error;
import com.eat.just.model.Restaurant;

import java.util.List;

/**
 */

public interface ListContract {
    interface View {

    }

    interface Presenter {

    }

    interface Model extends CancelCallback {
        void fetchRestaurants(String postCode, OnRestaurantsFetchListener restaurantsFetchListener);
    }

    interface OnRestaurantsFetchListener {
        void onSuccess(List<Restaurant> restaurants);

        void onFailure(Error error);
    }
}
