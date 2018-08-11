package com.coding.framework.mvp.ui.historic;

import android.widget.EditText;

import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.MvpView;

import java.util.HashMap;
import java.util.List;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

public interface HistoricMvpView extends MvpView {

    void addTranslations(List<TranslationResponse> translationResponse);

    HashMap<String, String> getCheckedLanguages();

    EditText getTranslateEditText();

}
