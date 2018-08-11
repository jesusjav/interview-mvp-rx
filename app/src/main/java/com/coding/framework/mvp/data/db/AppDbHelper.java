package com.coding.framework.mvp.data.db;

import com.coding.framework.mvp.data.db.model.DaoMaster;
import com.coding.framework.mvp.data.db.model.DaoSession;
import com.coding.framework.mvp.data.db.model.Translation;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertTranslation(final Translation translation) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getTranslationDao().insert(translation);
            }
        });
    }

    @Override
    public Observable<List<Translation>> getAllTranslations() {
        return Observable.fromCallable(new Callable<List<Translation>>() {
            @Override
            public List<Translation> call() throws Exception {
                return mDaoSession.getTranslationDao().loadAll();
            }
        });
    }


}
