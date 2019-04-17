package com.mkolakog.reddit.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;

    private static final int VISIBLE_THRESHOLD = 1;

    public PaginationListener(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = (LinearLayoutManager) layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();

        if (shouldLoadMore(totalItemCount, lastVisibleItemPosition)) {
            onLoadMore();
        }
    }

    private boolean shouldLoadMore(int totalItemCount, int lastVisibleItemPosition) {
        if ((lastVisibleItemPosition + VISIBLE_THRESHOLD) >= totalItemCount) {
            return true;
        }
        return false;
    }

    public boolean shouldLoadMore_test(int totalItemCount, int lastVisibleItemPosition) {
        return shouldLoadMore(totalItemCount, lastVisibleItemPosition);
    }

    abstract public void onLoadMore();
}

