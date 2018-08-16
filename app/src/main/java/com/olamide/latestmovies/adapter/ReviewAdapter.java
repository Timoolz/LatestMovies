package com.olamide.latestmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.Review;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{



    private List<Review> reviewList;
    private Context context;

    private final ReviewAdapterOnClickListener reviewClickListener;

    public ReviewAdapter(List<Review> reviews, Context context, ReviewAdapterOnClickListener reviewClickListener) {
        this.reviewList = reviews;
        this.context = context;
        this.reviewClickListener = reviewClickListener;
    }


    public void setReviewList(List<Review> reviews){
        this.reviewList = reviews;
        notifyDataSetChanged();
    }


    public interface  ReviewAdapterOnClickListener {
        void onClickListener(Review review);
    }


    public  class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.tv_author)
        TextView tAuthor;

        @BindView(R.id.tv_content)
        TextView tContent;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            reviewClickListener.onClickListener(reviewList.get(adapterPosition));

        }
    }


    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.review_item, viewGroup, false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {

        Review currentReview = reviewList.get(position);

        String author = currentReview.getAuthor();
        String content = currentReview.getContent();

        if(content.length() > 100){
            content = content.substring(0,100) + "...";
        }

        //Set values
        holder.tAuthor.setText(author);
        holder.tContent.setText(content);

    }



    @Override
    public int getItemCount() {
        if(reviewList != null){
            return reviewList.size();
        }else {
            return 0;
        }
    }
    
    
    
}
