package com.mkolakog.reddit;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.di.component.ApplicationComponent;
import com.mkolakog.reddit.di.component.DaggerApplicationComponent;
import com.mkolakog.reddit.di.module.ApplicationModule;
import com.mkolakog.reddit.utils.AppLogger;

import javax.inject.Inject;

public class App extends Application {
    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }



}
