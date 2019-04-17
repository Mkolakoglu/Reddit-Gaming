package com.mkolakog.reddit.utils;

import com.mkolakog.reddit.data.network.model.RedditResponse;

import java.util.ArrayList;

public class RedditResponseBuilder {
    RedditResponse redditResponse;

    public RedditResponseBuilder() {
        redditResponse = new RedditResponse();
        redditResponse.setData(new RedditResponse.Data());
        redditResponse.getData().setRedditDataList(new ArrayList<RedditResponse.RedditData>());
    }

    public RedditResponseBuilder withChild(RedditResponse.RedditData reddit) {
        redditResponse.getData().getRedditDataList().add(reddit);
        return this;
    }

    public RedditResponseBuilder itemCount(int count) {
        for (int i = 0; i < 25; i++) {
            withChild(new RedditDataBuilder().build());
        }
        return this;
    }

    public RedditResponse build() {
        return redditResponse;
    }

}



