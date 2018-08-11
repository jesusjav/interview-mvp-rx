package com.coding.framework.mvp.di.module;

import android.app.Application;
import android.content.Context;

import com.coding.framework.mvp.BuildConfig;
import com.coding.framework.mvp.R;
import com.coding.framework.mvp.data.AppDataManager;
import com.coding.framework.mvp.data.DataManager;
import com.coding.framework.mvp.data.db.AppDbHelper;
import com.coding.framework.mvp.data.db.DbHelper;
import com.coding.framework.mvp.data.network.ApiService;
import com.coding.framework.mvp.data.network.RetrofitApiService;
import com.coding.framework.mvp.data.prefs.AppPreferencesHelper;
import com.coding.framework.mvp.data.prefs.PreferencesHelper;
import com.coding.framework.mvp.di.ApplicationContext;
import com.coding.framework.mvp.di.DatabaseInfo;
import com.coding.framework.mvp.di.PreferenceInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Jesus Morales on 10-08-2018.
 */
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
    @DatabaseInfo
    String provideDatabaseName() {
        return BuildConfig.DATABASE_NAME;
    }

    @Provides
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return BuildConfig.PREFERENCES;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }


    @Provides
    @Singleton
    ApiService provideRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build().create(ApiService.class);
    }

    @Provides
    @Singleton
    RetrofitApiService provideRetrofitDataService(ApiService apiService) {
        return new RetrofitApiService(apiService);
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
