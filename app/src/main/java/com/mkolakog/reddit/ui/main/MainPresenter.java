package com.mkolakog.reddit.ui.main;

import com.androidnetworking.error.ANError;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BasePresenter;
import com.mkolakog.reddit.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V>{

    private static final String TAG = "MainPresenter";
    private boolean isLoading = false;

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        consumerSuccess = null;
        consumerFail = null;
    }

    @Override
    public void RequestData(String after) {
        if (!isLoading) {
            requestGamingList(after);
        }
    }

    public void requestGamingList(String after) {
        isLoading = true;
        getMvpView().showLoading();
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
            if (redditResponse.getData() != null) {
                getMvpView().updateResponseData(redditResponse);
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


}
