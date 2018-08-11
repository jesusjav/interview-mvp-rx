package com.coding.framework.mvp.ui.translator;

import android.support.annotation.StringRes;
import android.widget.EditText;

import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.MvpView;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

public interface MainMvpView extends MvpView {

    void addTranslations(List<TranslationResponse> translationResponse);

    void showDialogWithLangs(HashMap<String, String> langs);

    HashMap<String, String> getCheckedLanguages();

    EditText getTranslateEditText();

    void showErrorOnEditText(@StringRes int res);

}
