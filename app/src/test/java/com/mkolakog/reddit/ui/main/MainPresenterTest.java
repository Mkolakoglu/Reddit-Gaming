package com.mkolakog.reddit.ui.main;

import com.mkolakog.reddit.data.DataManager;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.utils.RedditResponseBuilder;
import com.mkolakog.reddit.utils.RxJavaSchedulersTestRule;
import com.mkolakog.reddit.utils.rx.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static io.reactivex.Single.just;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersOverrideTestRule = new RxJavaSchedulersTestRule();
    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;

    private MainPresenter<MainMvpView> mMainPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mMainPresenter = new MainPresenter<>(
                mMockDataManager,
                testSchedulerProvider,
                compositeDisposable);
        mMainPresenter.onAttach(mMockMainMvpView);
    }

    @Test
    public void testGetGamingList_success_shouldUpdateResponse() {
        RedditResponseBuilder builder = new RedditResponseBuilder();
        RedditResponse fakeResponse = builder.itemCount(25).build();
        when(mMockDataManager.getGamingListCall("")).thenReturn(just(fakeResponse));

        mMainPresenter.requestGamingList("");

        verify(mMockMainMvpView).showLoading();
        verify(mMockMainMvpView).updateResponseData(fakeResponse);
        verify(mMockMainMvpView).hideLoading();
        verifyNoMoreInteractions(mMockMainMvpView);

    }

    @Test
    public void testGetGamingList_fail_shouldNotUpdateList() {
        when(mMockDataManager.getGamingListCall("")).thenReturn(Single.error(new RuntimeException("error")));

        mMainPresenter.requestGamingList("");

        verify(mMockMainMvpView).showLoading();
        verify(mMockMainMvpView).hideLoading();
        verifyNoMoreInteractions(mMockMainMvpView);
    }

}
