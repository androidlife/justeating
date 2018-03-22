package com.eat.just.screen.restaurants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.eat.just.Extras;
import com.eat.just.R;
import com.eat.just.screen.restaurants.search.SearchInputActivity;

public class RestaurantListActivity extends AppCompatActivity {

    private static final int RETURN_FROM_POSTCODE = 0x1;
    private MenuItem postCodeMenuItem;

    private String postCode = "SE19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void goToSearchActivity() {
        startActivityForResult(new Intent(this, SearchInputActivity.class),
                RETURN_FROM_POSTCODE);
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
            goToSearchActivity();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RETURN_FROM_POSTCODE && resultCode == Activity.RESULT_OK && data != null
                && data.hasExtra(Extras.SEARCH_QUERY)) {
            postCode = data.getStringExtra(Extras.SEARCH_QUERY);
            invalidateOptionsMenu();
        }
    }
}
