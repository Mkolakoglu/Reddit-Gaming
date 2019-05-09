package com.mkolakog.reddit.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RedditResponse {

    @Expose
    @SerializedName("data")
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @Expose
        @SerializedName("children")
        private ArrayList<RedditData> redditDataList;

        @Expose
        @SerializedName("after")
        private String after = "";


        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public ArrayList<RedditData> getRedditDataList() {
            return redditDataList;
        }

        public void setRedditDataList(ArrayList<RedditData> redditList) {
            this.redditDataList = redditList;
        }
    }

    public static class RedditData extends BaseEntity {
        @Expose
        @SerializedName("data")
        private Reddit reddit;

        public Reddit getReddit() {
            return reddit;
        }

        public void setReddit(Reddit reddit) {
            this.reddit = reddit;
        }
    }

    public static class Reddit {

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("score")
        private String score;

        @Expose
        @SerializedName("subreddit")
        private String subreddit;

        @Expose
        @SerializedName("id")
        private String id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getSubreddit() {
            return subreddit;
        }

        public void setSubreddit(String subreddit) {
            this.subreddit = subreddit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}
