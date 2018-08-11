package com.coding.framework.mvp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.coding.framework.mvp.di.ApplicationContext;
import com.coding.framework.mvp.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

}
