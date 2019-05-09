package com.mkolakog.reddit.ui.main;

import android.os.Bundle;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.data.network.model.DataHolder;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BasePresenter;
import com.mkolakog.reddit.utils.rx.SchedulerProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V>{

    private static final String TAG = "MainPresenter";
    private boolean isLoading = false;

    private static final String KEY_REDDIT_LIST = "KEY_REDDIT_LIST";
    private static final String KEY_AFTER_PARAM = "KEY_AFTER_PARAM";

    @Inject
    Gson gson;
    @Inject
    DataHolder dataHolder;

    Consumer<RedditResponse> consumerSuccess;
    Consumer<Throwable> consumerFail;


    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        setUpConsumers();
        requestData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        consumerSuccess = null;
        consumerFail = null;
    }

    @Override
    public void requestData() {
        if (!isLoading) {
            requestGamingList(dataHolder.getNextAfterParam());
        }
    }

    @Override
    public void onSave(Bundle outState) {
        String redditListJson = gson.toJson(dataHolder.getRedditDataList());
        outState.putString(KEY_REDDIT_LIST, redditListJson);
        outState.putString(KEY_AFTER_PARAM, dataHolder.getNextAfterParam());
    }

    @Override
    public void onRestore(Bundle savedInstanceState) {
        String after = savedInstanceState.getString(KEY_AFTER_PARAM);
        String redditListJson = savedInstanceState.getString(KEY_REDDIT_LIST, "[]");
        Type type = new TypeToken<ArrayList<RedditResponse.RedditData>>() {}.getType();
        ArrayList<RedditResponse.RedditData> redditDataList = gson.fromJson(redditListJson, type);

        dataHolder.setRedditData(redditDataList);
        dataHolder.setNextAfterParam(after);

        getMvpView().updateData(dataHolder.getRedditDataList());
        if (redditDataList.size() == 0) {
            requestData();
        }
    }

    public void requestGamingList(String afterParam) {
        isLoading = true;
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getGamingListCall(afterParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumerSuccess, consumerFail));
    }

    private void setUpConsumers() {
        //onSuccess
        consumerSuccess = redditResponse -> {
            if (!isViewAttached()) {
                return;
            }
            if (redditResponse.getData() != null) {
                handleResponse(redditResponse);
            }
            getMvpView().hideLoading();
            isLoading = false;
        };

        //onFail
        consumerFail = throwable -> {
            if (!isViewAttached()) {
                return;
            }
            getMvpView().hideLoading();
            isLoading = false;

            if (throwable instanceof ANError) {
                ANError anError = (ANError) throwable;
                handleApiError(anError);
            }
        };
    }

    private void handleResponse(RedditResponse redditResponse) {
        dataHolder.handleResponse(redditResponse);
        getMvpView().updateData(dataHolder.getRedditDataList());
    }


}
