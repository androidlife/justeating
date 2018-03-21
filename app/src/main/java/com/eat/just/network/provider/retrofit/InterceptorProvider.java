package com.eat.just.network.provider.retrofit;

import com.eat.just.network.Config;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 */

public class InterceptorProvider {

    public HttpLoggingInterceptor getLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    public Interceptor getAuthorizationInterceptor() {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Authorization", Config.HEADER_AUTHORIZATION)
                    .addHeader("Accept-Language", Config.HEADER_ACCEPT_LANGUAGE_GB);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }
}
