package com.mkolakog.reddit.ui.main;

import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BaseMvpView;

import java.util.List;

public interface MainMvpView extends BaseMvpView {

    void updateResponseData(RedditResponse redditResponse);

}