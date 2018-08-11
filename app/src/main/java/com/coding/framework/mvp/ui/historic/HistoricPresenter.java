package com.coding.framework.mvp.ui.historic;

import android.text.TextUtils;

import com.coding.framework.mvp.R;
import com.coding.framework.mvp.data.DataManager;
import com.coding.framework.mvp.data.db.model.Translation;
import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class HistoricPresenter<V extends HistoricMvpView> extends BasePresenter<V>
        implements HistoricMvpPresenter<V> {

    private static final String TAG = "MainPresenter";

    @Inject
    public HistoricPresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void startTranslation() {

        Set<String> checkedLanguages = getMvpView().getCheckedLanguages().keySet();
        final String textToTranslate = getMvpView().getTranslateEditText().getText().toString();

        if (TextUtils.isEmpty(textToTranslate)) {
            getMvpView().showMessage(R.string.error_data_fill);
            return;
        }
        if (checkedLanguages.isEmpty()) {
            getMvpView().showMessage(R.string.error_no_target_language);
            return;
        }

        getMvpView().showLoading();

        Observable.fromArray(checkedLanguages)
                .flatMapIterable(new Function<Set<String>, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(Set<String> strings) throws Exception {
                        return strings;
                    }
                }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return "en-" + s;
            }
        }).flatMap(new Function<String, ObservableSource<TranslationResponse>>() {
            @Override
            public ObservableSource<TranslationResponse> apply(String s) throws Exception {
                return getDataManager().getTranslation(s, textToTranslate).timeout(10000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread()).doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().showMessage("We couldn't get all result but this are captured");
                            }
                        });
            }
        }).doOnNext(new Consumer<TranslationResponse>() {
            @Override
            public void accept(TranslationResponse translationResponse) throws Exception {
                Translation translationDB = new Translation();
                translationDB.setText(translationResponse.getLang());
                translationDB.setTextTranslated(translationResponse.getData().get(0));
                getDataManager().insertTranslation(translationDB);
            }
        }).toList()
                .subscribe(new SingleObserver<List<TranslationResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<TranslationResponse> translationResponses) {
                        getMvpView().addTranslations(translationResponses);
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                    }
                });

    }

    @Override
    public void loadRequestFromHistoric() {
        getCompositeDisposable().add(
        getDataManager().getAllTranslations()
                .flatMapIterable(new Function<List<Translation>, Iterable<Translation>>() {
                    @Override
                    public Iterable<Translation> apply(List<Translation> translations) throws Exception {
                        return translations;
                    }
                })
                .map(new Function<Translation, TranslationResponse>() {

                    @Override
                    public TranslationResponse apply(Translation translation) throws Exception {
                        TranslationResponse translationResponse = new TranslationResponse();
                        translationResponse.setLang(translation.getText());
                        List<String> dataTranslated = new ArrayList<>();
                        dataTranslated.add(translation.getTextTranslated());
                        translationResponse.setData(dataTranslated);
                        return translationResponse;
                    }
                }).toList()
                .subscribe(new Consumer<List<TranslationResponse>>() {
                    @Override
                    public void accept(List<TranslationResponse> translationResponses) throws Exception {
                        getMvpView().addTranslations(translationResponses);
                    }
                }));
    }


}
