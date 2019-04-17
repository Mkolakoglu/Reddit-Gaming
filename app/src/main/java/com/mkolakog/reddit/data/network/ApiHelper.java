package com.mkolakog.reddit.data.network;

import com.mkolakog.reddit.data.network.model.RedditResponse;
import io.reactivex.Single;

public interface ApiHelper {
    Single<RedditResponse> getGamingListCall(String after);
}
