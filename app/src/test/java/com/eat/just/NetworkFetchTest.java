package com.eat.just;

import com.eat.just.model.Restaurants;
import com.eat.just.network.provider.retrofit.ApiManager;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class NetworkFetchTest {

    public static final String POST_CODE = "se19";

    @Test
    public void testNormalRestaurantsFetch() {
        Restaurants restaurants = ApiManager.getApiService().getRestaurantsFromPostCode(POST_CODE)
                .blockingFirst();
        assertTrue(restaurants != null);
        assertTrue(restaurants.restaurantList != null);
    }


}