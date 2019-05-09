package com.mkolakog.reddit.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mkolakog.reddit.R;
import com.mkolakog.reddit.data.network.model.RedditResponse;
import com.mkolakog.reddit.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedditAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public interface Callback{
        void onItemClick(String itemId);
    }

    private ArrayList<RedditResponse.RedditData> mRedditList;
    private Callback mCallback;

    public RedditAdapter(ArrayList<RedditResponse.RedditData> redditList) {
        mRedditList = redditList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RedditViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reddit_view, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mRedditList.size();
    }

    public void addItems(List<RedditResponse.RedditData> redditList) {
        mRedditList.addAll(redditList);
        notifyDataSetChanged();
    }

    public void setList(ArrayList<RedditResponse.RedditData> redditList) {
        this.mRedditList = redditList;
        notifyDataSetChanged();
    }

    public ArrayList<RedditResponse.RedditData> getList() {
        return mRedditList;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public class RedditViewHolder extends BaseViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvScore)
        TextView tvScore;

        @BindView(R.id.tvSubReddit)
        TextView tvSubReddit;

        public RedditViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            super.onBind(position);
            tvTitle.setText(mRedditList.get(position).getReddit().getTitle());
            tvScore.setText(mRedditList.get(position).getReddit().getScore());
            tvSubReddit.setText(mRedditList.get(position).getReddit().getSubreddit());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onItemClick(mRedditList.get(getCurrentPosition()).getReddit().getId());
                }
            });

        }

        @Override
        protected void clear() {
            tvTitle.setText("");
            tvScore.setText("");
            tvSubReddit.setText("");
        }
    }
}
