package com.mkolakog.reddit.ui.splash;

import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.utils.rx.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    @Mock
    SplashMvpView mockSplashMvpView;
    @Mock
    DataManager mMockDataManager;
    @Mock
    CompositeDisposable mMockCompositeDisposable;

    SplashPresenter splashPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        splashPresenter = new SplashPresenter<>(
                mMockDataManager,
                testSchedulerProvider,
                mMockCompositeDisposable);
        splashPresenter.onAttach(mockSplashMvpView);

    }

    @Test
    public void testRequestStartTimerWithoutNetworkConnection_shouldShowConnectionError() {
        Mockito.when( mockSplashMvpView.isNetworkConnected()).thenReturn(false);

        splashPresenter.requestStartTimer();

        verify(mockSplashMvpView).onError(R.string.connection_error);
    }

}
