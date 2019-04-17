package com.mkolakog.reddit.ui.splash;


import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.ui.base.BasePresenter;
import com.mkolakog.reddit.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    public static final int ACTIVITY_START_DELAY = 2000;

    @Inject
    public SplashPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    public void requestStartTimer() {
        if (!isNetworkConnected()) {
            getMvpView().onError(R.string.connection_error);
        } else {
            startTimer(ACTIVITY_START_DELAY);
        }
    }

    public void startTimer(int duration) {
        mFollowPositionHandler.sendEmptyMessageDelayed(0, duration);
    }

    private Handler mFollowPositionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           getMvpView().startMainActivity();
        }
    };

    private boolean isNetworkConnected() {
        return getMvpView().isNetworkConnected();
    }

}
