package com.olamide.latestmovies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.SortType;
import com.olamide.latestmovies.adapter.MovieAdapter;
import com.olamide.latestmovies.bean.Movie;
import com.olamide.latestmovies.bean.TMDBMovieResponse;
import com.olamide.latestmovies.network.TMDBMoviesService;
import com.olamide.latestmovies.utils.RecyclerViewUtils;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String VIEW_MOVIE_OBJECT = "movieToView";

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    private List<Movie> movieList = new ArrayList<>();

    private MovieAdapter mAdapter;
    GridLayoutManager layoutManager;


    // to help regulate loading of more items
    private boolean loading = false;
    private int previousItems;
    private int visibleItemCount;
    private int totalItemCount;


    private int currentPage = 1;
    private int totalPages = 1;

    private SortType currentCategory = SortType.POPULAR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        int spanCount = RecyclerViewUtils.getSpanCount(mRecyclerView, this.getResources().getDimension(R.dimen.movie_layout_width));

        layoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MovieAdapter(movieList,  getApplicationContext(), this);


        if(currentCategory.equals(SortType.POPULAR)){
            getPopularMovies();
        }
        else if(currentCategory.equals(SortType.TOP_RATED)) {
            getTopRatedMovies();
        }

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check if it is a scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    previousItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        loading = true;
                        if ((visibleItemCount + previousItems) >= totalItemCount) {

                            if (currentPage < totalPages) {
                                currentPage++;
                                if(currentCategory.equals(SortType.POPULAR)){
                                    getPopularMovies();
                                }
                                else if(currentCategory.equals(SortType.TOP_RATED)) {
                                    getTopRatedMovies();
                                }
                            }
                        }
                        loading = false;
                    }
                }
            }
        });




    }




    public void getPopularMovies(){


        TMDBMoviesService.getMoviesBypopularity(Config.TMDB_API_KEY, currentPage, new Callback<TMDBMovieResponse>() {
            @Override
            public void onResponse(Call<TMDBMovieResponse> call, Response<TMDBMovieResponse> response) {
                Log.e(TAG, call.request().toString());

                Log.e(TAG, ReflectionToStringBuilder.toString(response));
                totalPages = response.body().getTotalPages();
                movieList.addAll(response.body().getResults());

                mAdapter.setMovieList(movieList);



            }

            @Override
            public void onFailure(Call<TMDBMovieResponse> call, Throwable t) {

                Log.e(TAG, "ERROR  " +t.toString());
            }
        });

    }


    public void getTopRatedMovies(){


        TMDBMoviesService.getMoviesByTopRated(Config.TMDB_API_KEY, currentPage, new Callback<TMDBMovieResponse>() {
            @Override
            public void onResponse(Call<TMDBMovieResponse> call, Response<TMDBMovieResponse> response) {
                Log.e(TAG, call.request().toString());

                Log.e(TAG, ReflectionToStringBuilder.toString(response));
                totalPages = response.body().getTotalPages();
                movieList.addAll(response.body().getResults());

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
        intent.putExtra(VIEW_MOVIE_OBJECT, movie);

        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.by_rating:

                currentPage = 1;
                currentCategory = SortType.TOP_RATED;
                movieList.clear();
                getTopRatedMovies();

                return true;

            case R.id.by_popularity:

                currentPage = 1;
                currentCategory = SortType.POPULAR;
                movieList.clear();
                getPopularMovies();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }




}
