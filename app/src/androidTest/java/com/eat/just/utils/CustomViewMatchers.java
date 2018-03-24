package com.eat.just.utils;

import android.support.test.espresso.remote.annotation.RemoteMsgConstructor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 */

public class CustomViewMatchers {

    public static Matcher<View> isSwipeRefreshing() {
        return new IsSwipeRefreshingMatcher();
    }

    static final class IsSwipeRefreshingMatcher extends TypeSafeMatcher<View> {
        @RemoteMsgConstructor
        private IsSwipeRefreshingMatcher() {
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("is SwipeRefreshLayout isRefreshing");
        }

        @Override
        public boolean matchesSafely(View view) {
            if (view instanceof SwipeRefreshLayout) {
                return view.getVisibility() == View.VISIBLE
                        && ((SwipeRefreshLayout) view).isRefreshing();
            }
            return false;
        }
    }
}
