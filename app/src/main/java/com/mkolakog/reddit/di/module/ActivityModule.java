package com.mkolakog.reddit.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mkolakog.reddit.data.network.model.DataHolder;
import com.mkolakog.reddit.di.ActivityContext;
import com.mkolakog.reddit.di.PerActivity;
import com.mkolakog.reddit.ui.main.MainMvpPresenter;
import com.mkolakog.reddit.ui.main.MainMvpView;
import com.mkolakog.reddit.ui.main.MainPresenter;
import com.mkolakog.reddit.ui.main.RedditAdapter;
import com.mkolakog.reddit.ui.splash.SplashMvpPresenter;
import com.mkolakog.reddit.ui.splash.SplashMvpView;
import com.mkolakog.reddit.ui.splash.SplashPresenter;
import com.mkolakog.reddit.utils.rx.AppSchedulerProvider;
import com.mkolakog.reddit.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    RedditAdapter provideRedditAdapter() {
        return new RedditAdapter((new ArrayList<>()));
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    DataHolder provideDataHolder() {
        return new DataHolder(new ArrayList<>());
    }

}
