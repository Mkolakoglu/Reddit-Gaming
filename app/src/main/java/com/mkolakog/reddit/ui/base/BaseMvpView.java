package com.mkolakog.reddit.ui.base;

import androidx.annotation.StringRes;

public interface BaseMvpView {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

}
