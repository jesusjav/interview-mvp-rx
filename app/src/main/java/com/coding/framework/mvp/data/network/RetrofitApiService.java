package com.coding.framework.mvp.data.network;

import com.coding.framework.mvp.data.network.model.LangResponse;
import com.coding.framework.mvp.data.network.model.TranslationResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class RetrofitApiService {

    private ApiService apiService;

    @Inject
    public RetrofitApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<TranslationResponse> getTranslation(String lang, String text) {
        return apiService.getTranslation(lang, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LangResponse> getAvailableLang() {
        return apiService.getAvailableLang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
