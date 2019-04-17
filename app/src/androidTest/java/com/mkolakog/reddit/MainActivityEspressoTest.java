package com.mkolakog.reddit;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;

import com.mkolakog.reddit.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void checkRvItemCountOnActivityStart_shouldBe25() {
        mainActivityTestRule.launchActivity(null);
        waitFor(2000);

        onView(withId(R.id.rvReddit)).check(assertItemCountRecyclerView(25));
    }

    @Test
    public void checkProgressBarOnStart_shouldBeShown() {
        mainActivityTestRule.launchActivity(null);

        onView(withId(R.id.pb_loading)).check(matches(isDisplayed()));
    }

    @Test
    public void checkProgressBarAfterDataReceived_shouldBeGone() {
        mainActivityTestRule.launchActivity(null);
        waitFor(3000);

        onView(withId(R.id.pb_loading)).check(doesNotExist());
    }

    @Test
    public void checkScreenRotationChange_shouldHaveSameItemCount() {
        mainActivityTestRule.launchActivity(null);
        waitFor(3000);

        rotateScreen(mainActivityTestRule.getActivity());
        waitFor(500);

        onView(withId(R.id.rvReddit)).check(assertItemCountRecyclerView(25));
    }

    private void waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ViewAssertion assertItemCountRecyclerView(int count) {
        return (view, noViewFoundException) -> {
            final RecyclerView recyclerView = (RecyclerView) view;
            final int actualCount = recyclerView.getAdapter().getItemCount();
            if (actualCount != count) {
                throw new AssertionError("RecyclerView has " + actualCount + " while expected " + count);
            }
        };
    }

    public static void rotateScreen(Activity activity) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final int orientation = InstrumentationRegistry.getTargetContext()
                .getResources()
                .getConfiguration()
                .orientation;
        final int newOrientation = (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        activity.setRequestedOrientation(newOrientation);

        getInstrumentation().waitForIdle(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("Screen rotation failed", e);
        }
    }



}
