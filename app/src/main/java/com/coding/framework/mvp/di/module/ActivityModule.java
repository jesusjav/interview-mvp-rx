package com.coding.framework.mvp.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.coding.framework.mvp.di.ActivityContext;
import com.coding.framework.mvp.di.PerActivity;
import com.coding.framework.mvp.ui.translator.MainMvpPresenter;
import com.coding.framework.mvp.ui.translator.MainMvpView;
import com.coding.framework.mvp.ui.translator.MainPresenter;
import com.coding.framework.mvp.ui.splash.SplashMvpPresenter;
import com.coding.framework.mvp.ui.splash.SplashMvpView;
import com.coding.framework.mvp.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

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
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideLoginPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
