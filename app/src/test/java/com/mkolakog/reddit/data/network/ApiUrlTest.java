package com.mkolakog.reddit.data.network;

import org.junit.Assert;
import org.junit.Test;

public class ApiUrlTest {

    @Test
    public void testAfterURL() {
        String url = ApiURL.getAfterUrl("after");
        Assert.assertEquals("Api URL does not work properly", "https://www.reddit.com/r/gaming/top.json?after=after", url);
    }

    @Test
    public void testAfterURLEmpty() {
        String url = ApiURL.getAfterUrl("");
        Assert.assertEquals("Api URL does not work properly", "https://www.reddit.com/r/gaming/top.json", url);
    }

    @Test
    public void testBeforeURL() {
        String url = ApiURL.getBefore("before");
        Assert.assertEquals("Api URL does not work properly", "https://www.reddit.com/r/gaming/top.json?before=before", url);
    }

    @Test
    public void testBeforeURLEmpty() {
        String url = ApiURL.getBefore("");
        Assert.assertEquals("Api URL does not work properly", "https://www.reddit.com/r/gaming/top.json", url);
    }

    @Test
    public void testClickUrl() {
        String url = ApiURL.getClickUrl("myId");
        Assert.assertEquals("Api URL does not work properly", "https://www.reddit.com/r/gaming/comments/myId", url);
    }

}
