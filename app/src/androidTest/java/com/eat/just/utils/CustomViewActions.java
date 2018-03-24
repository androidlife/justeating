package com.eat.just.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 */

public class CustomViewActions {

    public static ViewAction recyclerScrollTo(final int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                //just checking whether thew view is recyclerview and is displayed
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "recyclerScrollto:";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                if (recyclerView != null) {
                    recyclerView.scrollToPosition(position);
                }

            }
        };
    }
}
