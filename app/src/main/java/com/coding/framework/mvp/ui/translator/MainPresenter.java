package com.coding.framework.mvp.ui.translator;

import android.text.TextUtils;

import com.coding.framework.mvp.BuildConfig;
import com.coding.framework.mvp.R;
import com.coding.framework.mvp.data.DataManager;
import com.coding.framework.mvp.data.db.model.Translation;
import com.coding.framework.mvp.data.network.model.LangResponse;
import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.BasePresenter;

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

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void loadAvailableLang() {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getAvailableLang().subscribe(new Consumer<LangResponse>() {
                    @Override
                    public void accept(LangResponse langResponse) throws Exception {
                        getMvpView().getCheckedLanguages().clear();
                        getMvpView().showDialogWithLangs(langResponse.getLang());
                        getMvpView().hideLoading();
                    }
                }));
    }

    @Override
    public void startTranslation() {
        Set<String> checkedLanguages = getMvpView().getCheckedLanguages().keySet();
        final String textToTranslate = getMvpView().getTranslateEditText().getText().toString();

        if (TextUtils.isEmpty(textToTranslate)) {
            getMvpView().showErrorOnEditText(R.string.error_data_fill);
            return;
        }
        if (checkedLanguages.isEmpty()) {
            getMvpView().showMessage(R.string.error_no_target_language);
            return;
        }

        getMvpView().showLoading();

        Observable.fromIterable(checkedLanguages)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return BuildConfig.LANG + "-" + s;
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
                translationDB.setText(textToTranslate + " to " + translationResponse.getLang());
                translationDB.setTextTranslated(translationResponse.getData().get(0));
                getDataManager().insertTranslation(translationDB).subscribe();
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


    //Im assuming we are going to translate to spanish by deep link.

    @Override
    public void translateDeepLink(final String text){
        Observable.just("en-es")
               .flatMap(new Function<String, ObservableSource<TranslationResponse>>() {
            @Override
            public ObservableSource<TranslationResponse> apply(String s) throws Exception {
                return getDataManager().getTranslation(s, text).timeout(10000, TimeUnit.MILLISECONDS)
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
                translationDB.setText(text + " to " + translationResponse.getLang());
                translationDB.setTextTranslated(translationResponse.getData().get(0));
                getDataManager().insertTranslation(translationDB).subscribe();
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
}
