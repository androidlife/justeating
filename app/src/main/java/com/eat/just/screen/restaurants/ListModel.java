package com.eat.just.screen.restaurants;

import com.eat.just.model.Error;
import com.eat.just.model.Restaurant;
import com.eat.just.network.provider.retrofit.ApiManager;
import com.eat.just.network.provider.retrofit.ApiService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class ListModel implements ListContract.Model {

    private boolean isCancelled = false;

    private ApiService apiService;

    public ListModel(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void cancel(boolean cancel) {
        isCancelled = cancel;
    }

    @Override
    public void fetchRestaurants(String postCode, ListContract.OnRestaurantsFetchListener restaurantsFetchListener) {
        apiService.getRestaurantsFromPostCode(postCode)
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
