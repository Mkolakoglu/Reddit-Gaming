package com.mkolakog.reddit.data.network;

import com.mkolakog.reddit.BuildConfig;

public final class ApiURL {

    public static String getBefore(String before) {
        if (before.equals("")) return BuildConfig.BASE_URL + "/r/gaming/top.json";
        return BuildConfig.BASE_URL + "/r/gaming/top.json?before=" + before;
    }

    static String getAfterUrl(String after) {
        if (after.equals("")) return BuildConfig.BASE_URL + "/r/gaming/top.json";
        return BuildConfig.BASE_URL + "/r/gaming/top.json?after=" + after;
    }

    public static String getClickUrl(String id) {
        return BuildConfig.BASE_URL + "/r/gaming/comments/" + id;
    }

}
