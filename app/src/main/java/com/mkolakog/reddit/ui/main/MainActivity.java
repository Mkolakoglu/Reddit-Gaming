package com.mkolakog.reddit.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.network.ApiURL;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BaseActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, RedditAdapter.Callback{

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    RedditAdapter redditAdapter;

    @Inject
    Gson sGson;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvReddit)
    RecyclerView rvReddit;

    private PaginationListener mPaginationListener;

    String after = "";
    private static final String KEY_REDDIT_LIST = "KEY_REDDIT_LIST";
    private static final String KEY_AFTER_PARAM = "KEY_AFTER_PARAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        if (savedInstanceState == null) {
            after = "";
            mPresenter.RequestData(after);
        }
        setUp();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        setupPaginationListener();
        setUpRecyclerView();
        redditAdapter.setCallback(this);
    }

    @Override
    public void updateResponseData(RedditResponse redditResponse) {
        redditAdapter.addItems(redditResponse.getData().getRedditDataList());
        after = redditResponse.getData().getAfter();
    }

    @Override
    protected void onSnackbarClick() {
        mPresenter.RequestData(after);
    }

    @Override
    public void onItemClick(String itemId) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiURL.getClickUrl(itemId)));
        startActivity(browserIntent);
    }

    private void setupPaginationListener() {
        mPaginationListener = new PaginationListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                mPresenter.RequestData(after);
            }
        };
    }

    private void setUpRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvReddit.getContext(),
                linearLayoutManager.getOrientation());
        rvReddit.setAdapter(redditAdapter);
        rvReddit.setLayoutManager(linearLayoutManager);
        rvReddit.addItemDecoration(dividerItemDecoration);
        rvReddit.addOnScrollListener(mPaginationListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String redditListJson = sGson.toJson(redditAdapter.getList());
        outState.putString(KEY_REDDIT_LIST, redditListJson);
        outState.putString(KEY_AFTER_PARAM, after);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String redditListJson = savedInstanceState.getString(KEY_REDDIT_LIST, "[]");
        Type type = new TypeToken<ArrayList<RedditResponse.RedditData>>() {}.getType();
        List<RedditResponse.RedditData> redditDataList = sGson.fromJson(redditListJson, type);
        redditAdapter.addItems(redditDataList);
        after = savedInstanceState.getString(KEY_AFTER_PARAM);
        if (redditDataList.size() == 0) {
            after = "";
            mPresenter.RequestData(after);
        }
    }

}
