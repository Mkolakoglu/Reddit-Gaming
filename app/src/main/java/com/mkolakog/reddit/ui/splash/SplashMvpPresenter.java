package com.mkolakog.reddit.ui.splash;

import com.mkolakog.reddit.di.PerActivity;
import com.mkolakog.reddit.ui.base.BaseMvpPresenter;

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends BaseMvpPresenter<V> {

    void requestStartTimer();

}
