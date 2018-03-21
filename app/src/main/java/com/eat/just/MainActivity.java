package com.eat.just;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eat.just.network.provider.retrofit.ApiManager;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiManager.getApiService().getRestaurantsFromPostCode("se19").subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(r -> {
                    Timber.d("Success");
                }, e -> {
                    Timber.e("Error ");
                });
    }
}
