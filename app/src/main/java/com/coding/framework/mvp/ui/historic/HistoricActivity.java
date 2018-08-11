package com.coding.framework.mvp.ui.historic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coding.framework.mvp.R;
import com.coding.framework.mvp.adapter.TranslationAdapter;
import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class HistoricActivity extends BaseActivity implements HistoricMvpView {

    @Inject
    HistoricPresenter<HistoricMvpView> mPresenter;

    @BindView(R.id.translate_recycler)
    RecyclerView mTranslateRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.text_to_translate)
    EditText mTranslateEditText;

    @BindView(R.id.btn_translate)
    Button mTranslateButton;

    TranslationAdapter mTranslationAdapter;

    HashMap<String, String> mCheckedList = new HashMap<>();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, HistoricActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(HistoricActivity.this);

        setUp();

        mPresenter.loadRequestFromHistoric();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.actionbar_title_historic);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTranslateRecyclerView.setLayoutManager(mLayoutManager);
        mTranslateRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTranslationAdapter = new TranslationAdapter(new ArrayList<TranslationResponse>());
        mTranslateRecyclerView.setAdapter(mTranslationAdapter);

        mTranslateEditText.setVisibility(View.GONE);
        mTranslateButton.setVisibility(View.GONE);
    }


    @Override
    public HashMap<String, String> getCheckedLanguages() {
        return mCheckedList;
    }

    @Override
    public EditText getTranslateEditText() {
        return mTranslateEditText;
    }

    @Override
    public void addTranslations(List<TranslationResponse> translationResponse) {
        mTranslationAdapter.addItems(translationResponse);
    }

    @OnClick(R.id.btn_translate)
    public void onClickTranslate() {
        mPresenter.startTranslation();
    }
}
