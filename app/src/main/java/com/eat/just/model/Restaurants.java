package com.eat.just.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 */

public class Restaurants {
    @SerializedName("Restaurants")
    public List<Restaurant> restaurantList;
    @SerializedName("HasErrors")
    public boolean hasError;
    @SerializedName("Errors")
    public List<FetchError> errors;
}
