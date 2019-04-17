package com.mkolakog.reddit.data.network.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    protected static Gson sGson;

    static {
        sGson = new Gson();
    }

    @Override
    public String toString() {
        return sGson.toJson(this);
    }
}
