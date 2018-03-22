package com.eat.just.screen.restaurants;

import com.eat.just.model.Error;
import com.eat.just.model.Restaurant;
import com.eat.just.model.Restaurants;
import com.eat.just.network.provider.retrofit.ApiManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class RestaurantsListProvider implements ListContract.Model {

    private boolean isCancelled = false;

    @Override
    public void cancel(boolean cancel) {
        isCancelled = cancel;
    }

    @Override
    public void fetchRestaurants(String postCode, ListContract.OnRestaurantsFetchListener restaurantsFetchListener) {
        ApiManager.getApiService().getRestaurantsFromPostCode(postCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        restaurants -> {
                            if (restaurants == null || restaurants.restaurantList == null) {
                                onFailure(new Error(Error.Type.Fetch), restaurantsFetchListener);
                                return;
                            }
                            onSuccess(restaurants.restaurantList, restaurantsFetchListener);
                        }, e -> {
                            onFailure(new Error(Error.Type.Fetch), restaurantsFetchListener);
                        }
                );
    }


    private void onSuccess(List<Restaurant> restaurants, ListContract.OnRestaurantsFetchListener restaurantsFetchListener) {
        if (isCancelled)
            return;
        restaurantsFetchListener.onSuccess(restaurants);
    }

    private void onFailure(Error error, ListContract.OnRestaurantsFetchListener restaurantsFetchListener) {
        if (isCancelled)
            return;
        restaurantsFetchListener.onFailure(error);
    }
}
