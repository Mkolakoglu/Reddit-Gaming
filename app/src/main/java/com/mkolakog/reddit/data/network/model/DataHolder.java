package com.mkolakog.reddit.data.network.model;

import java.util.ArrayList;

import javax.inject.Inject;

public class DataHolder {

    private ArrayList<RedditResponse.RedditData> mRedditDataList;
    private String nextAfterParam = "";

    public DataHolder (ArrayList<RedditResponse.RedditData> redditDataList) {
        mRedditDataList = new ArrayList<>();
    }


    public ArrayList<RedditResponse.RedditData> getRedditDataList() {
        return mRedditDataList;
    }

    public void handleResponse (RedditResponse redditResponse) {
        addRedditData(redditResponse.getData().getRedditDataList());
        nextAfterParam = redditResponse.getData().getAfter();
    }

    public void addRedditData(ArrayList<RedditResponse.RedditData> redditDataList) {
        mRedditDataList.addAll(redditDataList);
    }

    public void setRedditData(ArrayList<RedditResponse.RedditData> redditDataList) {
        mRedditDataList = redditDataList;
    }

    public String getNextAfterParam() {
        return nextAfterParam;
    }

    public void setNextAfterParam(String nextAfterParam) {
        this.nextAfterParam = nextAfterParam;
    }
}
