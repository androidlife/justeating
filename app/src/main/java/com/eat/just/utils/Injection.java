package com.eat.just.utils;

import com.eat.just.screen.restaurants.ListContract;
import com.eat.just.screen.restaurants.ListModel;

/**
 */

public class Injection {

    public static ListContract.Model provideListModel() {
        return new ListModel();
    }
}
