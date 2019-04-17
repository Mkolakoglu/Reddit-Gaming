package com.mkolakog.reddit.di.component;

import com.mkolakog.reddit.di.PerActivity;
import com.mkolakog.reddit.di.module.ActivityModule;
import com.mkolakog.reddit.ui.main.MainActivity;
import com.mkolakog.reddit.ui.splash.SplashActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SplashActivity activity);

}
