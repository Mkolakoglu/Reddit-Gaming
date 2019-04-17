package com.mkolakog.reddit.ui.base;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.utils.AppConstants;
import com.mkolakog.reddit.utils.rx.TestSchedulerProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

public class BasePrensenterTest {

    @Mock
    BaseMvpView mockBaseMvpView;
    @Mock
    DataManager mMockDataManager;


    private BasePresenter<BaseMvpView> mBasePresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mBasePresenter = new BasePresenter<>(
                mMockDataManager,
                testSchedulerProvider,
                compositeDisposable);
        mBasePresenter.onAttach(mockBaseMvpView);
    }

    @Test
    public void testHandleError_shouldShowDefaultError() {
        ANError error = null;

        mBasePresenter.handleApiError(error);

        verify(mockBaseMvpView).onError(R.string.api_default_error);
    }

    @Test
    public void testHandleError_shouldShowDefaultError2() {
        ANError error = new ANError();

        mBasePresenter.handleApiError(error);

        verify(mockBaseMvpView).onError(R.string.api_default_error);
    }

    @Test
    public void testHandleError_shouldShowDefaultError3() {
        ANError error = new ANError();
        error.setErrorCode(AppConstants.API_STATUS_CODE_LOCAL_ERROR);
        error.setErrorDetail(ANConstants.REQUEST_CANCELLED_ERROR);

        mBasePresenter.handleApiError(error);

        verify(mockBaseMvpView).onError(R.string.api_default_error);
    }

    @Test
    public void testHandleError_shouldShowOtherError() {
        ANError error = new ANError();
        String errorMessage = "other error";
        String otherError = "{message: 'other error'}";
        error.setErrorBody(otherError);
        error.setErrorDetail("anything");

        mBasePresenter.handleApiError(error);

        verify(mockBaseMvpView).onError(errorMessage);
    }


    @Test
    public void testHandleError_shouldShowConnectionError() {
        ANError error = new ANError();
        error.setErrorCode(AppConstants.API_STATUS_CODE_LOCAL_ERROR);
        error.setErrorDetail(ANConstants.CONNECTION_ERROR);

        mBasePresenter.handleApiError(error);

        verify(mockBaseMvpView).onError(R.string.connection_error);
    }

    @Test
    public void testOnDetach() {
        mBasePresenter.onDetach();

        Assert.assertEquals(mBasePresenter.getMvpView(), null);
        Assert.assertEquals(mBasePresenter.getCompositeDisposable().isDisposed(), true);
    }

}
