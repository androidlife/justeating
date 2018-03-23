package com.eat.just.screen.restaurants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.eat.just.Extras;
import com.eat.just.R;
import com.eat.just.screen.restaurants.search.SearchInputActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    private static final int RETURN_FROM_POSTCODE = 0x1;
    private MenuItem postCodeMenuItem;

    private String postCode = "SE19";

    private ListContract.Presenter presenter;

    @BindView(R.id.swipe_ref_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_restaurant_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        presenter = new ListPresenter(this);

        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurant_list, menu);
        postCodeMenuItem = menu.findItem(R.id.action_search_label);
        postCodeMenuItem.setTitle(postCode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            presenter.searchForPostCode();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETURN_FROM_POSTCODE && resultCode == Activity.RESULT_OK && data != null
                && data.hasExtra(Extras.SEARCH_QUERY)) {
            postCode = data.getStringExtra(Extras.SEARCH_QUERY);
            presenter.onNewPostCode();
        }
    }

    @Override
    public void navigateForPostCode() {
        startActivityForResult(new Intent(this, SearchInputActivity.class),
                RETURN_FROM_POSTCODE);
    }

    @Override
    public void updatePostCode() {
        invalidateOptionsMenu();
    }
}
