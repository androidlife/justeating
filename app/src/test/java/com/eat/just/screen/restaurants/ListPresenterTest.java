package com.eat.just.screen.restaurants;

import com.eat.just.model.Error;
import com.eat.just.model.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

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
        Mockito.verify(model).fetchRestaurants(Matchers.any(String.class), fetchListener.capture());
        fetchListener.getValue().onSuccess(new ArrayList<>());
        Mockito.verify(view).showInfo(Matchers.anyString());
        Mockito.verify(view).setData(Matchers.anyListOf(Restaurant.class));
        Mockito.verify(view).setViewState(ListContract.View.STATE_FETCHED);
    }



    @Test
    public void fetchFailureTest() {
        presenter.start(true);
        Mockito.verify(model).fetchRestaurants(Matchers.any(String.class), fetchListener.capture());
        fetchListener.getValue().onFailure(Matchers.any(Error.class));
        Mockito.verify(view).setViewState(ListContract.View.STATE_ERROR);
        Mockito.verify(view).showProgress(false);
        Mockito.verify(view).showError(true);
        Mockito.verify(view).showInfo(Matchers.anyString());
    }

    @Test
    public void onNewPostCodeTest() {
        presenter.onNewPostCode();
        Mockito.verify(model).fetchRestaurants(Matchers.any(String.class), fetchListener.capture());
        fetchListener.getValue().onSuccess(new ArrayList<>());
        Mockito.verify(view).showInfo(Matchers.anyString());
        Mockito.verify(view, Mockito.times(2)).setData(Matchers.anyListOf(Restaurant.class));
        Mockito.verify(view).setViewState(ListContract.View.STATE_FETCHED);

    }

    private void testCommonFetchCall(){
        Mockito.verify(model).cancel(false);
        Mockito.verify(view).showError(false);
        Mockito.verify(view).showProgress(true);
    }

    @Test
    public void retryTest() {
        presenter.retry();
        Mockito.verify(view).setData(Matchers.argThat(matchListWithSize(0)));
        Mockito.verify(view).setViewState(ListContract.View.STATE_EMPTY);
        testCommonFetchCall();
    }

    private ArgumentMatcher<List> matchListWithSize(final int size) {
        return new ArgumentMatcher<List>() {
            @Override
            public boolean matches(Object argument) {
                return ((List) argument).size() == size;
            }
        };
    }

}
