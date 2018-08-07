package com.olamide.latestmovies.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {




    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movieList = movies;
        this.context = context;
    }



    public static class MovieAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_movie)
        ImageView movieImage;

        public MovieAdapterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        String image_url = Config.TMDB_IMAGE_URL_PATH + movieList.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);



    }

    @Override
    public int getItemCount() {
        if(movieList != null){
            return movieList.size();
        }else {
            return 0;
        }
    }
}
