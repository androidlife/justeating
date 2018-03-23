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
    public boolean isOpenNow;
    @SerializedName("RatingStars")
    public float ratingStars;
    @SerializedName("CuisineTypes")
    public List<Cuisine> cuisines;
    @SerializedName("NumberOfRatings")
    public int numOfRatings;
    public String totalNumRatings;
    @SerializedName("Logo")
    public List<Logo> logos;

    private String allCuisines = null;
    private String logoUrl = null;

    public String getCuisines() {
        if (allCuisines == null) {
            allCuisines = "Cuisine: ";
            if (cuisines != null && cuisines.size() > 0) {
                for (int i = 0; i < cuisines.size(); ++i) {
                    allCuisines.concat(cuisines.get(i).name);
                    if (i != cuisines.size() - 1)
                        allCuisines.concat(", ");
                }
            }
        }

        return allCuisines;
    }

    public String getLogoUrl() {
        if (logoUrl == null) {
            logoUrl = "";
            if (logos != null && logos.size() > 0)
                logoUrl = logos.get(0).url;
        }
        return logoUrl;
    }

    public String getTotalNumberOfRatings() {
        if (totalNumRatings == null) {
            totalNumRatings = "";
            totalNumRatings.concat("( ").concat(String.valueOf(numOfRatings))
                    .concat(" )");
        }
        return totalNumRatings;

    }

}
