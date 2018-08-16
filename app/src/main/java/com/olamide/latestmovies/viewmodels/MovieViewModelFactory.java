package com.olamide.latestmovies.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.olamide.latestmovies.database.LatestMoviesDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final LatestMoviesDatabase mDb;
    private final int mMovieId;


    public MovieViewModelFactory(LatestMoviesDatabase database, int movieId) {
        mDb = database;
        mMovieId = movieId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieViewModel(mDb, mMovieId);
    }


}
