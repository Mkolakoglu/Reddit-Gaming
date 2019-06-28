package com.mkolakog.reddit.ui.main;

import com.androidnetworking.error.ANError;
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

    private static final String TAG = "MainPresenter";
    private boolean isLoading = false;

    private Consumer<RedditResponse> consumerSuccess;
    private Consumer<Throwable> consumerFail;

    DataHolder dataHolder;

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         DataHolder dataHolder) {
        super(dataManager, schedulerProvider, compositeDisposable);
        this.dataHolder = dataHolder;
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


    public void requestGamingList(String after) {
        handleShowLoading();
        getCompositeDisposable().add(getDataManager()
                .getGamingListCall(after)
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
            handleHideLoading();
            if (redditResponse.getData() != null) {
                handleResponse(redditResponse);
            }
        };

        //onFail
        consumerFail = throwable -> {
            if (!isViewAttached()) {
                return;
            }
            handleHideLoading();
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


    private void handleShowLoading() {
        isLoading = true;
        getMvpView().showLoading();
    }

    private void handleHideLoading() {
        getMvpView().hideLoading();
        isLoading = false;
    }

}
