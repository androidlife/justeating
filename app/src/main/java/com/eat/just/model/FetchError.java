package com.eat.just.model;

import com.google.gson.annotations.SerializedName;

/**
 */

public class FetchError {
    @SerializedName("ErrorType")
    public String errorType;
    @SerializedName("Message")
    public String errorMessage;
}
