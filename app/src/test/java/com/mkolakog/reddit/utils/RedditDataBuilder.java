package com.mkolakog.reddit.utils;

import com.mkolakog.reddit.data.network.model.RedditResponse;

public class RedditDataBuilder {
    RedditResponse.RedditData redditData;

    public RedditDataBuilder() {
        redditData = new RedditResponse.RedditData();
        redditData.setReddit(new RedditResponse.Reddit());
        redditData.getReddit().setId("id");
        redditData.getReddit().setScore("22");
        redditData.getReddit().setSubreddit("subreddit");
        redditData.getReddit().setTitle("title");

    }

    public RedditResponse.RedditData build() {
        return redditData;
    }


}