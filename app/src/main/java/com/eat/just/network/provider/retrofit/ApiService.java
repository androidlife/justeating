package com.eat.just.network.provider.retrofit;

import com.eat.just.model.Restaurants;
import com.eat.just.network.Api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 */

public interface ApiService {
    @GET(Api.URL_GET_RESTAURANTS)
    Observable<Restaurants> getRestaurantsFromPostCode(@Query(Api.QUERY) String postCode);
}
