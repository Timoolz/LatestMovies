package com.olamide.latestmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.TMDBMovieResponse;
import com.olamide.latestmovies.network.TMDBMoviesService;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.test_tv)
    TextView mTestTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        TMDBMoviesService.getMoviesByTopRated(Config.TMDB_API_KEY, new Callback<TMDBMovieResponse>() {
            @Override
            public void onResponse(Call<TMDBMovieResponse> call, Response<TMDBMovieResponse> response) {
                Log.e(TAG, call.request().toString());
                //movies = response.body().getResults();
                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                TMDBMovieResponse  movieResponse = response.body();
                Log.e(TAG, ReflectionToStringBuilder.toString(movieResponse));
                mTestTv.setText(ReflectionToStringBuilder.toString(movieResponse));

            }

            @Override
            public void onFailure(Call<TMDBMovieResponse> call, Throwable t) {

                Log.e(TAG, "ERROR  " +t.toString());
            }
        });



    }
}
