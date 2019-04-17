package com.mkolakog.reddit.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mkolakog.reddit.App;
import com.mkolakog.reddit.R;
import com.mkolakog.reddit.di.ActivityContext;
import com.mkolakog.reddit.di.component.ActivityComponent;
import com.mkolakog.reddit.di.component.DaggerActivityComponent;
import com.mkolakog.reddit.di.module.ActivityModule;
import com.mkolakog.reddit.utils.CommonUtils;
import com.mkolakog.reddit.utils.NetworkUtils;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView, BaseFragment.Callback  {

    @ActivityContext
    Context mContext;

    private ProgressDialog mProgressDialog;
    private ActivityComponent mActivityComponent;
    private Unbinder mUnBinder;

    private ViewGroup mSnackbarAnchor;

    private View.OnClickListener snackbarClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();
        mContext = this;
        findSnackbarAnchor();
        setupSnackbarClickListener();

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void startActivity(Class activity) {
        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showSnackbar(String text) {
        CommonUtils.snackbar(mSnackbarAnchor, text, Snackbar.LENGTH_LONG).show();
    }

    public void showSnackbar(int textResId) {
        CommonUtils.snackbar(mSnackbarAnchor, getString(textResId), Snackbar.LENGTH_LONG).show();
    }


    public void showSnackbar(int textResId, int actionResId) {
        Snackbar snackbar = CommonUtils.snackbar(mSnackbarAnchor, getString(textResId), getString(actionResId), snackbarClickListener, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    public void showSnackbar(String text, int actionResId) {
        Snackbar snackbar = CommonUtils.snackbar(mSnackbarAnchor, text, getString(actionResId), snackbarClickListener, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    public void showSnackbar(int textResId, int actionResId, View.OnClickListener actionClickListener) {
        Snackbar snackbar = CommonUtils.snackbar(mSnackbarAnchor, getString(textResId), getString(actionResId), actionClickListener, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    public void showSnackbar(String text, String action, View.OnClickListener actionClickListener) {
        Snackbar snackbar = CommonUtils.snackbar(mSnackbarAnchor, text, action, actionClickListener, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    private void setupSnackbarClickListener() {
        snackbarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSnackbarClick();
            }
        };
    }

    protected void onSnackbarClick() {

    }

    private void findSnackbarAnchor() {
        mSnackbarAnchor = (ViewGroup) findViewById(R.id.root);
        if (mSnackbarAnchor == null) {
            mSnackbarAnchor = findViewById(android.R.id.content);
        }
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackbar(message, R.string.api_retry_error);
        } else {
            showSnackbar(getString(R.string.some_error), R.string.try_again);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }


    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void setUp();


}
