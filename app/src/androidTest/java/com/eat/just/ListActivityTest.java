package com.eat.just;

import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eat.just.screen.restaurants.ListActivity;
import com.eat.just.utils.CustomIdlingResource;
import com.eat.just.utils.CustomViewActions;
import com.eat.just.utils.GeneralUtil;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.eat.just.utils.CustomViewActions.recyclerScrollTo;
import static com.eat.just.utils.CustomViewMatchers.isSwipeRefreshing;


@RunWith(AndroidJUnit4.class)
public class ListActivityTest {
    @Rule
    public ActivityTestRule<ListActivity> activityActivityTestRule
            = new ActivityTestRule<>(ListActivity.class);

    private static final int TIMEOUT_ESPRESSO = 60;
    private static final int TIMEOUT_FETCH = 6;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

    private CustomIdlingResource idlingResource;

    @Before
    public void init() {
        idlingResource = new CustomIdlingResource();
        IdlingPolicies.setMasterPolicyTimeout(TIMEOUT_ESPRESSO, TIMEOUT_UNIT);
    }

    @After
    public void clear() {
        IdlingRegistry.getInstance().unregister(idlingResource);

    }

    @Test
    public void fetchTest() {
        if (GeneralUtil.isConnectedToNetwork()) {
            onView(withId(R.id.swipe_ref_layout)).check(matches(isSwipeRefreshing()));
            IdlingRegistry.getInstance().register(idlingResource);
            idlingResource.startCountdown(TIMEOUT_FETCH, TIMEOUT_UNIT);

            int totalItems = 0;
            String value = CustomViewActions.getTextFromId(R.id.tv_total);
            if (!TextUtils.isEmpty(value)) {
                value = value.split("\\s")[0];
                totalItems = Integer.valueOf(value);
            }
            if (totalItems > 0)
                onView(withId(R.id.rv_restaurant_list)).perform(recyclerScrollTo(totalItems));
            else
                onView(withId(R.id.tv_total)).check(matches(isDisplayed()));
        } else {
            IdlingRegistry.getInstance().register(idlingResource);
            idlingResource.startCountdown(3, TIMEOUT_UNIT);
            onView(withId(R.id.tv_info)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_info)).perform(click());
        }
    }


    private int getCountFromInfo() {
        final int[] count = new int[1];
        onView(withId(R.id.tv_total)).perform(new ViewAction() {
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
                    String value = ((TextView) view).getText().toString();
                    if (!TextUtils.isEmpty(value)) {
                        value = value.split("\\s")[0];
                        count[0] = Integer.valueOf(value);
                    }
                }

            }
        });
        return count[0];
    }


}
