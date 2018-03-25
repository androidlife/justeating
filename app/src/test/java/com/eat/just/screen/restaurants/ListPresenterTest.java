package com.eat.just.screen.restaurants;

import com.eat.just.model.Error;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

/**
 */

public class ListPresenterTest {

    @Mock
    ListContract.Model model;
    @Mock
    ListContract.View view;
    @Captor
    ArgumentCaptor<ListContract.OnRestaurantsFetchListener> fetchListener;

    private ListContract.Presenter presenter;

    @Before
    public void setUpPresenter() {
        MockitoAnnotations.initMocks(this);
        presenter = new ListPresenter(view, model);

        Mockito.when(view.getPostCode()).thenReturn("SE19");
        Mockito.when(view.getViewState()).thenReturn(ListContract.View.STATE_EMPTY);
        Mockito.when(view.isConnectedToNetwork()).thenReturn(true);
    }

    @Test
    public void fetchSuccessTest() {
        presenter.start(true);
        fetchSuccess();
    }

    private void fetchSuccess() {
        Mockito.verify(model).fetchRestaurants(Matchers.any(String.class), fetchListener.capture());
        fetchListener.getValue().onSuccess(new ArrayList<>());
        Mockito.verify(view).setViewState(ListContract.View.STATE_FETCHED);
    }

    @Test
    public void fetchFailureTest() {
        presenter.start(true);
        Mockito.verify(model).fetchRestaurants(Matchers.any(String.class), fetchListener.capture());
        fetchListener.getValue().onFailure(Matchers.any(Error.class));
        Mockito.verify(view).setViewState(ListContract.View.STATE_ERROR);
    }

    @Test
    public void retryTest() {
        presenter.onNewPostCode();
        fetchSuccess();
    }

}
