package com.eat.just.screen.restaurants.search;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eat.just.Extras;
import com.eat.just.R;
import com.eat.just.base.PermissionActivity;
import com.eat.just.location.LocationContract;
import com.eat.just.location.PostalCodeProvider;
import com.eat.just.model.Error;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SearchInputActivity extends PermissionActivity {

    private SearchView searchView;

    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);
        ButterKnife.bind(this);

        postCodeProvider = new PostalCodeProvider(this);

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


    private LocationContract.PostCodeProvider postCodeProvider;

    private void fetchPostalCode() {
        showProgress(true);
        postCodeProvider.fetchPostCode(new LocationContract.OnPostCodeFetchListener() {
            @Override
            public void onPostCodeFetched(String postCode) {
                showProgress(false);
                searchView.setQuery(postCode, false);
            }

            @Override
            public void onPostCodeFetchError(Error error) {
                showProgress(false);
                Toast.makeText(SearchInputActivity.this, "Error Fetching PostCode",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        postCodeProvider.cancel(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        postCodeProvider.cancel(false);
    }

    private void showProgress(boolean show) {
        if (show) {
            searchView.setEnabled(false);
            tvInfo.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            searchView.setEnabled(true);
            tvInfo.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_location) {
            if (isPermissionGranted())
                fetchPostalCode();
            else
                requestPermission();
        }


        return true;
    }

    @Override
    protected String[] getPermissions() {
        return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
    }

    @Override
    protected void onPermissionGranted() {
        Timber.d("Permission Granted");
        fetchPostalCode();
    }

    @Override
    protected void onPermissionDenied(String[] permission) {
        Toast.makeText(this, "Need location permission to fetch post code",
                Toast.LENGTH_SHORT).show();
    }
}
