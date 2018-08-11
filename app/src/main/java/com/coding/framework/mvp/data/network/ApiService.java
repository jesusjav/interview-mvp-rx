package com.coding.framework.mvp.data.network;

import com.coding.framework.mvp.data.network.model.LangResponse;
import com.coding.framework.mvp.data.network.model.TranslationResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Jesus Morales on 10-08-2018.
 * key hardcoded for this particular case
 */

public interface ApiService {

    @GET("/api/v1.5/tr.json/translate?key=trnsl.1.1.20180618T160416Z.16b70a0930cc1557.e167183e5b19d460d8f212247551f90b60128a41")
    Observable<TranslationResponse> getTranslation(@Query("lang") String lang, @Query("text") String text);

    @GET("/api/v1.5/tr.json/getLangs?ui=en&key=trnsl.1.1.20180618T160416Z.16b70a0930cc1557.e167183e5b19d460d8f212247551f90b60128a41")
    Observable<LangResponse> getAvailableLang();
}
