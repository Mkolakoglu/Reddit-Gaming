package com.mkolakog.reddit.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.network.ApiURL;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BaseActivity;

import java.util.ArrayList;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, RedditAdapter.Callback{

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    RedditAdapter redditAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rvReddit)
    RecyclerView rvReddit;

    private PaginationListener mPaginationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
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
    public void updateData(ArrayList<RedditResponse.RedditData> redditDataList) {
        redditAdapter.setList(redditDataList);
    }

    @Override
    protected void onSnackbarClick() {
        mPresenter.requestData();
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
                mPresenter.requestData();
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

}
