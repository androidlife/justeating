package com.eat.just.screen.restaurants;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eat.just.Extras;
import com.eat.just.R;
import com.eat.just.model.Restaurant;
import com.eat.just.screen.restaurants.search.SearchInputActivity;
import com.eat.just.screen.restaurants.widgets.RestaurantListAdapter;
import com.eat.just.screen.restaurants.widgets.RowItemSpace;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_total)
    TextView tvTotal;


    private int viewState = ListContract.View.STATE_EMPTY;
    private RestaurantListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        presenter = new ListPresenter(this);
        swipeRefreshLayout.setEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        Resources resources = getResources();
        int left = resources.getDimensionPixelSize(R.dimen.row_space_left);
        int right = resources.getDimensionPixelSize(R.dimen.row_space_right);
        int top = resources.getDimensionPixelSize(R.dimen.row_space_top);
        int bottom = resources.getDimensionPixelSize(R.dimen.row_space_bottom);
        RowItemSpace rowItemSpace = new RowItemSpace(left, top, right, bottom);
        recyclerView.addItemDecoration(rowItemSpace);

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.start(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start(true);
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

    @Override
    public void showProgress(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public int getViewState() {
        return viewState;
    }

    @Override
    public void setViewState(int viewState) {
        this.viewState = viewState;
    }

    @Override
    public String getPostCode() {
        return postCode;
    }

    @Override
    public void showError(boolean show) {
        tvInfo.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showInfo(String infoMsg) {
        Toast.makeText(this, infoMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(List<Restaurant> restaurants) {
        if (listAdapter == null) {
            listAdapter = new RestaurantListAdapter(restaurants);
            recyclerView.setAdapter(listAdapter);
        } else {
            listAdapter.setRestaurants(restaurants);
        }

        tvTotal.setText(String.valueOf(restaurants.size()).concat(" Restaurants"));
    }

    @OnClick({R.id.tv_info})
    public void onClick(View view) {
        presenter.retry();
    }
}
