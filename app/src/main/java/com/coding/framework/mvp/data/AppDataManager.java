package com.coding.framework.mvp.data;


import android.content.Context;

import com.coding.framework.mvp.data.db.DbHelper;
import com.coding.framework.mvp.data.db.model.Translation;
import com.coding.framework.mvp.data.network.RetrofitApiService;
import com.coding.framework.mvp.data.network.model.LangResponse;
import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.data.prefs.PreferencesHelper;
import com.coding.framework.mvp.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final RetrofitApiService retrofitApiService;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          RetrofitApiService retrofitApiService) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        this.retrofitApiService = retrofitApiService;
    }

    @Override
    public Observable<Long> insertTranslation(Translation translation) {
        return mDbHelper.insertTranslation(translation);
    }

    @Override
    public Observable<List<Translation>> getAllTranslations() {
        return mDbHelper.getAllTranslations();
    }

    @Override
    public Observable<TranslationResponse> getTranslation(String lang, String text) {
        return retrofitApiService.getTranslation(lang, text);
    }

    @Override
    public Observable<LangResponse> getAvailableLang() {
        return retrofitApiService.getAvailableLang();
    }

}
