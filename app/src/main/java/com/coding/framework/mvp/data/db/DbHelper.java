package com.coding.framework.mvp.data.db;

import com.coding.framework.mvp.data.db.model.Translation;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

public interface DbHelper {

    Observable<Long> insertTranslation(Translation translation);

    Observable<List<Translation>> getAllTranslations();

}
