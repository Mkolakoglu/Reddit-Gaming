package com.mkolakog.reddit.ui.base;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

public interface BaseMvpView {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

}
