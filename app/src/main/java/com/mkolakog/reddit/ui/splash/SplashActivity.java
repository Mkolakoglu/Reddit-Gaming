package com.mkolakog.reddit.ui.splash;

import android.os.Bundle;

import com.mkolakog.reddit.R;
import com.mkolakog.reddit.ui.base.BaseActivity;
import com.mkolakog.reddit.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {
    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(SplashActivity.this);
        setUp();

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        mPresenter.requestStartTimer();
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    protected void onSnackbarClick() {
        mPresenter.requestStartTimer();
    }
}
