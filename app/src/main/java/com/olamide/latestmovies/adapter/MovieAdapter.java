package com.olamide.latestmovies.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    private final MovieAdapterOnClickListener movieClickListener;

    public MovieAdapter(List<Movie> movies, Context context, MovieAdapterOnClickListener movieClickListener) {
        this.movieList = movies;
        this.context = context;
        this.movieClickListener = movieClickListener;
    }


    public void setMovieList(List<Movie> movies){
        this.movieList = movies;
        notifyDataSetChanged();
    }


    public interface  MovieAdapterOnClickListener {
        void onClickListener(Movie movie);
    }


    public  class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.iv_movie)
        ImageView movieImage;

        public MovieAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            movieClickListener.onClickListener(movieList.get(adapterPosition));

        }
    }


    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);

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
