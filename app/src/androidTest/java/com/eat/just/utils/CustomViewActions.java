package com.eat.just.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eat.just.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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

    public static String getTextFromId(int id) {
        final String[] texts = new String[1];
        onView(withId(id)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "Hackish way of getting textview value";
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof TextView) {
                    texts[0] = ((TextView) view).getText().toString();

                }

            }
        });
        return texts[0];
    }
}
