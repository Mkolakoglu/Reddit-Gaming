package com.mkolakog.reddit;

import com.mkolakog.reddit.ui.splash.SplashActivity;

import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by user on 17.04.2019.
 */

public class SplashActivityEspressoTest {
    public ActivityTestRule<SplashActivity> splashActivityTestRule = new ActivityTestRule<>(SplashActivity.class, false, false);

    @Test
    public void testSplashTitle_shouldBeSameWithAppName() {
        splashActivityTestRule.launchActivity(null);

        onView(withId(R.id.tvSplash))
                .check(matches(isDisplayed()))
                .check(matches(withText("Reddit Gaming")));
    }

}
