package com.mkolakog.reddit.ui.main;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.data.network.model.DataHolder;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BasePresenter;
import com.mkolakog.reddit.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V>{

    private boolean isLoading = false;

    @Inject
    Gson gson;
    @Inject
    DataHolder dataHolder;

    private Consumer<RedditResponse> consumerSuccess;
    private Consumer<Throwable> consumerFail;


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
