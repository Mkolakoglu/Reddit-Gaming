package com.mkolakog.reddit.data;

import com.mkolakog.reddit.data.network.ApiHelper;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper) {
        mApiHelper = apiHelper;
    }

    @Override
    public Single<RedditResponse> getGamingListCall(String after) {
        return mApiHelper.getGamingListCall(after);
    }


}
