package com.olamide.latestmovies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.adapter.MovieAdapter;
import com.olamide.latestmovies.bean.Movie;
import com.olamide.latestmovies.bean.TMDBMovieResponse;
import com.olamide.latestmovies.network.TMDBMoviesService;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    private List<Movie> movieList;

    private MovieAdapter mAdapter;

    // variables to help with pagination
    private int current_page = 1;
    private int total_pages = 1;

    // variable to help keep track of category
    private boolean top_rated = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAdapter = new MovieAdapter(movieList,  getApplicationContext(), this);


        if(top_rated){getTopRatedMovies();}
        else {getPopularMovies();}

        mRecyclerView.setAdapter(mAdapter);

    }




    public void getPopularMovies(){


        TMDBMoviesService.getMoviesBypopularity(Config.TMDB_API_KEY, new Callback<TMDBMovieResponse>() {
            @Override
            public void onResponse(Call<TMDBMovieResponse> call, Response<TMDBMovieResponse> response) {
                Log.e(TAG, call.request().toString());

                Log.e(TAG, ReflectionToStringBuilder.toString(response));
                movieList = response.body().getResults();
                mAdapter.setMovieList(movieList);

            }

            @Override
            public void onFailure(Call<TMDBMovieResponse> call, Throwable t) {

                Log.e(TAG, "ERROR  " +t.toString());
            }
        });

    }



    public void getTopRatedMovies(){


        TMDBMoviesService.getMoviesByTopRated(Config.TMDB_API_KEY, new Callback<TMDBMovieResponse>() {
            @Override
            public void onResponse(Call<TMDBMovieResponse> call, Response<TMDBMovieResponse> response) {
                Log.e(TAG, call.request().toString());

                Log.e(TAG, ReflectionToStringBuilder.toString(response));
                movieList = response.body().getResults();
                mAdapter.setMovieList(movieList);

            }

            @Override
            public void onFailure(Call<TMDBMovieResponse> call, Throwable t) {

                Log.e(TAG, "ERROR  " +t.toString());
            }
        });

    }


    @Override
    public void onClickListener(Movie movie) {

        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movieToView", movie.getId());

        startActivity(intent);

    }
}
