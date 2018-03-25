package com.eat.just.network.provider.retrofit;

import com.eat.just.network.Api;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 */

public class Interceptors {

    public static HttpLoggingInterceptor getLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    public static Interceptor getAuthorizationInterceptor() {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader(Api.HEADER_ACCEPT_LANGUAGE_KEY, Api.HEADER_ACCEPT_LANGUAGE_VALUE)
                    .addHeader(Api.HEADER_AUTHORIZATION_KEY, Api.HEADER_AUTHORIZATION_VALUE);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }
}
