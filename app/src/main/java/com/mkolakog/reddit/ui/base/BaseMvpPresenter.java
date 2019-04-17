package com.mkolakog.reddit.ui.base;

import com.androidnetworking.error.ANError;

public interface BaseMvpPresenter<V extends BaseMvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);

}
