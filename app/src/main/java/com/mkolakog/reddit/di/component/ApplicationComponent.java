package com.mkolakog.reddit.di.component;

import android.app.Application;
import android.content.Context;


import com.google.gson.Gson;
import com.mkolakog.reddit.App;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.di.ApplicationContext;
import com.mkolakog.reddit.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();

    Gson getGson();
}