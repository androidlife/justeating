package com.eat.just.screen.restaurants;

import com.eat.just.base.CancelCallback;
import com.eat.just.model.Error;
import com.eat.just.model.Restaurant;

import java.util.List;

/**
 */

public interface ListContract {
    interface View {
        void navigateForPostCode();

        void updatePostCode();

        int getViewState();

        void setViewState(int viewState);

        void showError(boolean show);

        void showProgress(boolean show);

        void showInfo(String infoMsg);

        String getPostCode();

        void setData(List<Restaurant> restaurants);

        int STATE_EMPTY = 0x1, STATE_ERROR = 0x2, STATE_FETCHED = 0x3;
    }

    interface Presenter {
        void searchForPostCode();

        void onNewPostCode();

        void start(boolean start);

        void retry();
    }

    interface Model extends CancelCallback {
        void fetchRestaurants(String postCode, OnRestaurantsFetchListener restaurantsFetchListener);
    }

    interface OnRestaurantsFetchListener {
        void onSuccess(List<Restaurant> restaurants);

        void onFailure(Error error);
    }


}
