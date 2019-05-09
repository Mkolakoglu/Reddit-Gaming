package com.mkolakog.reddit.ui.main;

import com.mkolakog.reddit.di.PerActivity;
import com.mkolakog.reddit.ui.base.BaseMvpPresenter;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends BaseMvpPresenter<V> {

    void requestData();

}
