package com.eat.just.network.provider.retrofit;

import com.eat.just.model.Restaurants;
import com.eat.just.network.Urls;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 */

public interface ApiService {
    @GET(Urls.RESTAURANTS)
    Observable<Restaurants> getRestaurantsFromPostCode(@Query("q") String postCode);
}
