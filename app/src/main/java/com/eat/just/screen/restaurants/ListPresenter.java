package com.eat.just.screen.restaurants;

import com.eat.just.model.Error;
import com.eat.just.model.Restaurant;
import com.eat.just.utils.GeneralUtil;

import java.util.List;

/**
 */

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;
    private ListContract.Model model;

    public ListPresenter(ListContract.View view) {
        this.view = view;
        model = new ListModel();
    }

    @Override
    public void searchForPostCode() {
        //TODO check whether view is in fetching state or not
        view.navigateForPostCode();
    }

    @Override
    public void onNewPostCode() {
        view.updatePostCode();
    }

    @Override
    public void start(boolean start) {
        if (start) {
            fetchRestaurantsByPostCode();
        } else {
            model.cancel(true);
        }
    }

    private void fetchRestaurantsByPostCode() {
        if (view.getViewState() == ListContract.View.STATE_EMPTY) {
            if (!GeneralUtil.isConnectedToNetwork()) {
                setViewError("No internet connection");
                return;
            }
            model.cancel(false);
            view.showError(false);
            view.showProgress(true);
            model.fetchRestaurants(view.getPostCode(), new ListContract.OnRestaurantsFetchListener() {
                @Override
                public void onSuccess(List<Restaurant> restaurants) {
                    view.showProgress(false);
                    if (restaurants.size() == 0) {
                        view.showInfo("No restaurants found");
                    }
                    view.setData(restaurants);
                    view.setViewState(ListContract.View.STATE_FETCHED);
                }

                @Override
                public void onFailure(Error error) {
                    setViewError("Error fetching restaurants");
                }
            });
        }
    }

    private void setViewError(String errorMsg) {
        view.showProgress(false);
        view.setViewState(ListContract.View.STATE_ERROR);
        view.showError(true);
        view.showInfo(errorMsg);
    }
}
