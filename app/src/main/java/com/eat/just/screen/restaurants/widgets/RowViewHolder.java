package com.eat.just.screen.restaurants.widgets;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eat.just.R;
import com.eat.just.image.ImageLoadOptions;
import com.eat.just.image.ImageLoader;
import com.eat.just.model.Restaurant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class RowViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_view)
    ImageView imageView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_total_rating)
    TextView tvTotalRating;
    @BindView(R.id.tv_available)
    TextView tvAvailable;
    @BindView(R.id.tv_cuisine)
    TextView tvCuisine;

    Drawable available, notAvailable;

    ImageLoadOptions imageLoadOptions;


    public RowViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        available = itemView.getResources().getDrawable(R.drawable.ic_visibility, null);
        notAvailable = itemView.getResources().getDrawable(R.drawable.ic_visibility_off, null);


        Resources resources = itemView.getResources();
        int imgWidth = resources.getDimensionPixelSize(R.dimen.img_width);
        int imgHeight = resources.getDimensionPixelSize(R.dimen.img_height);

        imageLoadOptions = new ImageLoadOptions.Builder().setErrorDrawable(R.drawable.ic_placeholder_error)
                .setPlaceHolder(R.drawable.ic_placeholder)
                .setResizeWidthHeight(imgWidth, imgHeight)
                .build();
    }

    public void setData(Restaurant restaurant) {
        tvTitle.setText(restaurant.name);
        tvTotalRating.setText(restaurant.getTotalNumberOfRatings());
        tvCuisine.setText(restaurant.getCuisines());
        ratingBar.setRating(restaurant.ratingStars);
        ImageLoader.loadImage(restaurant.getLogoUrl(), imageView, imageLoadOptions);
        if (restaurant.isOpenNow) {
            tvAvailable.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility, 0, 0, 0);
            tvAvailable.setText("Open Now");
        } else {
            tvAvailable.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_visibility_off, 0, 0, 0);
            tvAvailable.setText("Closed Now");
        }


    }
}
