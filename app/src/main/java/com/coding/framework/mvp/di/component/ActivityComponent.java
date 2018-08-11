package com.coding.framework.mvp.di.component;

import com.coding.framework.mvp.di.PerActivity;
import com.coding.framework.mvp.di.module.ActivityModule;
import com.coding.framework.mvp.ui.historic.HistoricActivity;
import com.coding.framework.mvp.ui.translator.MainActivity;
import com.coding.framework.mvp.ui.splash.SplashActivity;

import dagger.Component;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(HistoricActivity activity);

    void inject(SplashActivity activity);

}
