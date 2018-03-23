package com.eat.just.screen.restaurants;

/**
 */

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;

    public ListPresenter(ListContract.View view) {
        this.view = view;
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
}
