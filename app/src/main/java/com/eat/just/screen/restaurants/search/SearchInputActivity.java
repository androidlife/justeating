package com.eat.just.screen.restaurants.search;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;

import com.eat.just.Extras;
import com.eat.just.R;
import com.eat.just.base.PermissionActivity;
import com.eat.just.location.LocationContract;
import com.eat.just.location.PostalCodeRetriever;
import com.eat.just.model.Error;

import timber.log.Timber;

public class SearchInputActivity extends PermissionActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_input, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setQueryHint(getString(R.string.info_search_post_code));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void sendSearchQuery(String query) {
        Timber.d("Query to be searched = %s", query);
        if (TextUtils.isEmpty(query)) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Extras.SEARCH_QUERY, query);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    protected String[] getPermissions() {
        return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
    }

    @Override
    protected void onPermissionGranted() {
        Timber.d("Permission Granted");
    }

    @Override
    protected void onPermissionDenied(String[] permission) {
        Timber.d("Permission denied for");
        for (String perm : permission)
            Timber.d("PERM = %s", perm);
    }
}
