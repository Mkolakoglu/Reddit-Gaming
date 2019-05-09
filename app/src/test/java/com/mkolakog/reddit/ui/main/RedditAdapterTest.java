package com.mkolakog.reddit.ui.main;

import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.utils.RedditResponseBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RedditAdapterTest {

    private RedditAdapter redditAdapter;

    private RedditResponse fakeResponse;

    @Before
    public void setUp() throws Exception {
        RedditResponseBuilder builder = new RedditResponseBuilder();
        fakeResponse = builder.itemCount(25).build();
        redditAdapter = new RedditAdapter(fakeResponse.getData().getRedditDataList());
    }

    @Test
    public void testGetList() {
        Assert.assertEquals(fakeResponse.getData().getRedditDataList(), redditAdapter.getList());
    }

    @Test
    public void testGetItemCount() {
        Assert.assertEquals(fakeResponse.getData().getRedditDataList().size(), redditAdapter.getItemCount());
    }



}
