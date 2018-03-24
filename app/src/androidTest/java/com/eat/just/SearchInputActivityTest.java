package com.eat.just;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.text.TextUtils;

import com.eat.just.screen.restaurants.search.SearchInputActivity;
import com.eat.just.utils.CustomIdlingResource;
import com.eat.just.utils.CustomViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 */
@RunWith(AndroidJUnit4.class)
public class SearchInputActivityTest {
    @Rule
    public IntentsTestRule<SearchInputActivity> activityActivityTestRule
            = new IntentsTestRule<>(SearchInputActivity.class);


    public void postCodeInputTest() {
        final String postCode = "EC1A";
        onView(withId(android.support.v7.appcompat.R.id.search_src_text))
                .perform(typeText(postCode));
        submitSearchedItem(postCode);
    }

    public void submitSearchedItem(String searchedItem) {
        onView(withId(android.support.v7.appcompat.R.id.search_src_text))
                .perform(ViewActions.pressImeActionButton());
        intending(IntentMatchers.hasExtra(Extras.SEARCH_QUERY, searchedItem));
    }

    @Test
    public void postCodeInputViaLocationTest() {
        onView(withId(R.id.action_location)).perform(click());
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject allowPermissions = uiDevice.findObject(new UiSelector().text("ALLOW"));
        if (allowPermissions.exists()) {
            try {
                allowPermissions.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        CustomIdlingResource customIdlingResource = new CustomIdlingResource();
        IdlingRegistry.getInstance().register(customIdlingResource);
        customIdlingResource.startCountdown(4, TimeUnit.SECONDS);

        String postCode = CustomViewActions.getTextFromId(android.support.v7.appcompat.R.id.search_src_text);
        if (!TextUtils.isEmpty(postCode)) {
            submitSearchedItem(postCode);
        }
        IdlingRegistry.getInstance().unregister(customIdlingResource);
    }
}
