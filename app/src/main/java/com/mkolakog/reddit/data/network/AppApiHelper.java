package com.mkolakog.reddit.data.network;

import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    public AppApiHelper() {

    }

    @Override
    public Single<RedditResponse> getGamingListCall(String after) {

        return Rx2AndroidNetworking.get(ApiURL.getAfterUrl(after))
                .build()
                .getObjectSingle(RedditResponse.class);
    }
}

