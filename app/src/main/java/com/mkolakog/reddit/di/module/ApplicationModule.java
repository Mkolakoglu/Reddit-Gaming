package com.mkolakog.reddit.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.AppDataManager;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.data.network.ApiHelper;
import com.mkolakog.reddit.data.network.AppApiHelper;
import com.mkolakog.reddit.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }


    @Provides
    @Singleton
    static Gson provideGson() {
        return new Gson();
    }
}
