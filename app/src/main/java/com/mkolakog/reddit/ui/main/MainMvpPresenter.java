package com.mkolakog.reddit.ui.main;

import android.os.Bundle;

import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.di.PerActivity;
import com.mkolakog.reddit.ui.base.BaseMvpPresenter;

import java.util.ArrayList;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends BaseMvpPresenter<V> {

    void requestData();

    void onSave(Bundle outState);

    void onRestore(Bundle savedInstanceState);

}
