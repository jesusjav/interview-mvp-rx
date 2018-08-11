package com.coding.framework.mvp.ui.translator;


import com.coding.framework.mvp.di.PerActivity;
import com.coding.framework.mvp.ui.base.MvpPresenter;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void loadAvailableLang();

    void startTranslation();

    void translateDeepLink(String text);


}
