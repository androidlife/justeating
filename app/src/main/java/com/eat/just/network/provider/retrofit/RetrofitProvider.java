package com.eat.just.network.provider.retrofit;

import com.eat.just.BuildConfig;
import com.eat.just.network.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */

public class RetrofitProvider {
    static volatile Retrofit retrofit;

    private RetrofitProvider() {

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitProvider.class) {
                if (retrofit == null) {
                    OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
                    okhttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES);
                    okhttpClientBuilder.readTimeout(1, TimeUnit.MINUTES);
                    if (BuildConfig.DEBUG)
                        okhttpClientBuilder.addInterceptor(Interceptors.getLoggingInterceptor(HttpLoggingInterceptor.Level.BODY));
                    okhttpClientBuilder.addInterceptor(Interceptors.getAuthorizationInterceptor());
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Api.URL_BASE)
                            .client(okhttpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }

        }
        return retrofit;
    }
}
