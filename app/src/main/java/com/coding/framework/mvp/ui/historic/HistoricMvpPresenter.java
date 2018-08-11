package com.coding.framework.mvp.ui.historic;


import com.coding.framework.mvp.di.PerActivity;
import com.coding.framework.mvp.ui.base.MvpPresenter;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@PerActivity
public interface HistoricMvpPresenter<V extends HistoricMvpView> extends MvpPresenter<V> {

    void startTranslation();

    void loadRequestFromHistoric();

}
