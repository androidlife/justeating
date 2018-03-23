package com.eat.just.screen.restaurants.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eat.just.R;
import com.eat.just.model.Restaurant;

import java.util.List;

/**
 */

public class ListAdapter extends RecyclerView.Adapter<RowViewHolder> {


    private List<Restaurant> restaurants;

    public ListAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        holder.setData(getItemAtPosition(position));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public Restaurant getItemAtPosition(int position) {
        return restaurants.get(position);
    }
}
