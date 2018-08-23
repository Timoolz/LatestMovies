package com.olamide.latestmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{



    private List<Trailer> trailerList;
    private Context context;

    private final TrailerAdapterOnClickListener trailerClickListener;

    public TrailerAdapter(List<Trailer> trailers, Context context, TrailerAdapterOnClickListener trailerClickListener) {
        this.trailerList = trailers;
        this.context = context;
        this.trailerClickListener = trailerClickListener;
    }


    public void setTrailerList(List<Trailer> trailers){
        this.trailerList = trailers;
        notifyDataSetChanged();
    }


    public interface  TrailerAdapterOnClickListener {
        void onTrailerClickListener(Trailer trailer);
    }


    public  class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.iv_trailer)
        ImageView ivTrailer;


        public TrailerAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            trailerClickListener.onTrailerClickListener(trailerList.get(adapterPosition));

        }
    }


    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.trailer_item, viewGroup, false);

        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {

        Picasso.with(context)
                .load(R.drawable.youtuber)
                .placeholder(R.drawable.loader)
                .error(R.drawable.errorr)
                .into(holder.ivTrailer);

    }



    @Override
    public int getItemCount() {
        if(trailerList != null){
            return trailerList.size();
        }else {
            return 0;
        }
    }



}

