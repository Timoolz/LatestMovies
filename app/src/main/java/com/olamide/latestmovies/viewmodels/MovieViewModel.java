package com.olamide.latestmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.olamide.latestmovies.bean.Movie;
import com.olamide.latestmovies.database.LatestMoviesDatabase;

public class MovieViewModel extends ViewModel{

    private LiveData<Movie> movieLiveData;


    public MovieViewModel(LatestMoviesDatabase database, int movieId) {
        movieLiveData = database.movieDao().loadMovieById(movieId);
    }


    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }
}
