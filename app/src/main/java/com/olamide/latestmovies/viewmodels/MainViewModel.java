package com.olamide.latestmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.olamide.latestmovies.bean.Movie;
import com.olamide.latestmovies.database.LatestMoviesDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MainViewModel(Application application) {
        super(application);


        LatestMoviesDatabase database = LatestMoviesDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the favourite Movies from the DataBase");

            movies = database.movieDao().loadAllMovies();

    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }


}
