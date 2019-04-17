package com.mkolakog.reddit.ui.main;

import android.support.v7.widget.LinearLayoutManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class PaginationListenerTest {
    @Mock
    LinearLayoutManager layoutManager;

    PaginationListener paginationListener;

    @Before
    public void setUp() {
        paginationListener = new PaginationListener(layoutManager) {
            @Override
            public void onLoadMore() {

            }
        };
    }

    @Test
    public void testShouldLoadMore_shouldBeTrue() {
        boolean testResult = paginationListener.shouldLoadMore_test(25, 25);

        Assert.assertEquals(true, testResult);
    }

    @Test
    public void testShouldLoadMore_shouldBeFalse() {
        boolean testResult = paginationListener.shouldLoadMore_test(25, 0);

        Assert.assertEquals(false, testResult);
    }

}
