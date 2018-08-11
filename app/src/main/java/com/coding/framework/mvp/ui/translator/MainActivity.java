package com.coding.framework.mvp.ui.translator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.coding.framework.mvp.R;
import com.coding.framework.mvp.adapter.TranslationAdapter;
import com.coding.framework.mvp.data.network.model.TranslationResponse;
import com.coding.framework.mvp.ui.base.BaseActivity;
import com.coding.framework.mvp.ui.historic.HistoricActivity;

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

public class MainActivity extends BaseActivity implements MainMvpView {

    public static final String WORD_DEEP_PARAM = "word";

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.translate_recycler)
    RecyclerView mTranslateRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.text_to_translate)
    EditText mTranslateEditText;

    TranslationAdapter mTranslationAdapter;

    HashMap<String, String> mCheckedList = new HashMap<>();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MainActivity.this);

        setUp();

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            String wordToTranslate = uri.getQueryParameter(WORD_DEEP_PARAM);

            mPresenter.translateDeepLink(wordToTranslate);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTranslateRecyclerView.setLayoutManager(mLayoutManager);
        mTranslateRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTranslationAdapter = new TranslationAdapter(new ArrayList<TranslationResponse>());
        mTranslateRecyclerView.setAdapter(mTranslationAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showLang:
                mPresenter.loadAvailableLang();
                return true;
            case R.id.historic:
                showHistoric();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void showDialogWithLangs(HashMap<String, String> langs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_target);

        final String[] languageCode = langs.keySet().toArray(new String[0]);

        List<String> values = new ArrayList<>();
        for (String thing : langs.values()) {
            values.add(thing);
        }

        String[] languageName = values.toArray(new String[0]);
        boolean[] checkedItems = new boolean[languageCode.length];
        builder.setMultiChoiceItems(languageName, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked) {
                    mCheckedList.put(languageCode[which], languageCode[which]);
                } else {
                    mCheckedList.remove(languageCode[which]);
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.btn_translate)
    public void onClickTranslate() {
        mPresenter.startTranslation();
    }

    private void showHistoric() {
        Intent intent = HistoricActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showErrorOnEditText(@StringRes int res) {
        mTranslateEditText.setError(getString(res));
    }
}
