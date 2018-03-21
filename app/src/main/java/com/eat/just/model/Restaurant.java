package com.eat.just.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 */

public class Restaurant {
    @SerializedName("Id")
    public long id;
    @SerializedName("Name")
    public String name;
    @SerializedName("IsOpenNow")
    public boolean currentlyAvailable;
    @SerializedName("RatingStars")
    public float ratingStars;
    @SerializedName("CuisineTypes")
    public List<Cuisine> cuisines;
    @SerializedName("Logo")
    public List<Logo> logos;

}
