package com.coding.framework.mvp.di.component;

import android.app.Application;
import android.content.Context;

import com.coding.framework.mvp.MvpApp;
import com.coding.framework.mvp.data.DataManager;
import com.coding.framework.mvp.di.ApplicationContext;
import com.coding.framework.mvp.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}