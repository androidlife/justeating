package com.eat.just.network.provider.retrofit;

;

/**
 * Our Network service provider
 */

public class ApiManager {
    private static ApiService apiService;

    private ApiManager() {

    }

    public static synchronized ApiService getApiService() {
        if (apiService == null) {
            apiService = RetrofitProvider.getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

}
