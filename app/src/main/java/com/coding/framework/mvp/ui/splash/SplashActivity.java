package com.coding.framework.mvp.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.coding.framework.mvp.R;
import com.coding.framework.mvp.ui.base.BaseActivity;
import com.coding.framework.mvp.ui.translator.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openNextActivity();
            }
        }, 1000);
    }

    @Override
    public void openNextActivity() {
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();

      //TEST DEEPLINK
      /* String url = "translator://interview?word=hello";
       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
       startActivity(intent);*/
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
    }
}
