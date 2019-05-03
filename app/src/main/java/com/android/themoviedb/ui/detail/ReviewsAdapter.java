package com.android.themoviedb.ui.detail;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.themoviedb.R;
import com.android.themoviedb.model.Reviews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by twin on 07/12/16.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Reviews> reviewsList = new ArrayList<>();

    public void setMoviesReview(List<Reviews> reviews) {
        this.reviewsList.clear();
        this.reviewsList.addAll(reviews);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieReviewsHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Reviews review = reviewsList.get(position);

        if (holder instanceof MovieReviewsHolder){
            MovieReviewsHolder movieReviewsHolder = (MovieReviewsHolder) holder;

            ((MovieReviewsHolder) holder).mReview = review;
            ((MovieReviewsHolder) holder).mAuthorView.setText(review.getAuthor());
            ((MovieReviewsHolder) holder).mContentView.setText(review.getContent());


        }
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(@LayoutRes int resId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        }
    }

    public class MovieReviewsHolder extends Holder {
        public final View mView;
        @BindView(R.id.review_author)TextView mAuthorView;
        @BindView(R.id.review_content)TextView mContentView;
        public Reviews mReview;

        public MovieReviewsHolder(ViewGroup parent) {
            super(R.layout.list_item_reviews, parent);
            ButterKnife.bind(this, itemView);
            mView=parent;
        }
    }
}
