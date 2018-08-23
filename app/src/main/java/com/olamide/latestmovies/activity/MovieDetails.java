package com.olamide.latestmovies.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.adapter.ReviewAdapter;
import com.olamide.latestmovies.adapter.TrailerAdapter;
import com.olamide.latestmovies.bean.Movie;
import com.olamide.latestmovies.bean.Review;
import com.olamide.latestmovies.bean.TMDBReviewResponse;
import com.olamide.latestmovies.bean.TMDBVideoResponse;
import com.olamide.latestmovies.bean.Trailer;
import com.olamide.latestmovies.database.LatestMoviesDatabase;
import com.olamide.latestmovies.utils.AppExecutors;
import com.olamide.latestmovies.utils.network.TMDBMoviesService;
import com.olamide.latestmovies.viewmodels.MovieViewModel;
import com.olamide.latestmovies.viewmodels.MovieViewModelFactory;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static com.olamide.latestmovies.Config.YOUTUBE_BASE_URL;

public class MovieDetails extends AppCompatActivity implements ReviewAdapter.ReviewAdapterOnClickListener, TrailerAdapter.TrailerAdapterOnClickListener {

    private static final String TAG = MovieDetails.class.getSimpleName();
    public static final String LOAD_REVIEW_OBJECT = "reviewToLoad";

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_ratings)
    TextView tvRatings;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;

    @BindView(R.id.iv_movie_poster)
    ImageView ivPoster;

    @BindView(R.id.bt_favourite)
    Button btFavourite;


    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;

    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;

    private Movie movie;



    private boolean favouriteMovie;
    private LatestMoviesDatabase mDb;


    private List<Trailer> mTrailerList = new ArrayList<>();
    private TrailerAdapter trailerAdapter;
    private LinearLayoutManager trailerLayoutManager;

    private List<Review> mReviews = new ArrayList<>();
    private ReviewAdapter reviewAdapter;
    private LinearLayoutManager reviewLayoutManager;


    private int currentPage = 1;
    private int totalPages = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        movie =  getIntent().getParcelableExtra(MainActivity.VIEW_MOVIE_OBJECT);

        mDb = LatestMoviesDatabase.getInstance(getApplicationContext());


        reviewLayoutManager = new LinearLayoutManager(this);
        rvReviews.setLayoutManager(reviewLayoutManager);

        trailerLayoutManager = new LinearLayoutManager(this);
        rvTrailers.setLayoutManager(trailerLayoutManager);

        checkFavourite();
        if(favouriteMovie){
            //favouriteText = this.getResources().getString(R.string.remove_favourite);
            btFavourite.setText(R.string.remove_favourite);
        }else {
            //favouriteText = this.getResources().getString(R.string.add_favourite);
            btFavourite.setText(R.string.add_favourite);
        }

        reviewAdapter = new ReviewAdapter(mReviews,  getApplicationContext(), this);
        rvReviews.setAdapter(reviewAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        rvReviews.addItemDecoration(decoration);


        trailerAdapter = new TrailerAdapter(mTrailerList, getApplicationContext(), this);
        rvTrailers.setAdapter(trailerAdapter);


        fetchReviews();
        fetchTrailers();
        populateMovie();


    }


    private void populateMovie(){

        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getReleaseDate().substring(0,4));
        tvRatings.setText(Double.toString(movie.getVoteAverage()) + "/10");
        tvSynopsis.setText(movie.getOverview());


        String image_url = Config.TMDB_IMAGE_URL_PATH +movie.getPosterPath();
        Picasso.with(this)
                .load(image_url)
                .placeholder(R.drawable.loader)
                .error(R.drawable.errorr)
                .into(ivPoster);


    }



    @OnClick(R.id.bt_favourite)
    public void setFavourite(){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                if(favouriteMovie){
                    // Remove favourite
                    mDb.movieDao().deleteMovie(movie);
                    Log.e(TAG,"Successfully removed this movie from favourite  ");
                    favouriteMovie = false;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            btFavourite.setText(R.string.add_favourite);
                        }
                    });

                }else {
                    // Add favourite
                    movie.setCreatedAt(new Date());
                    mDb.movieDao().insertMovie(movie);
                    Log.e(TAG,"Successfully added this movie from favourite  ");
                    favouriteMovie = true;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            btFavourite.setText(R.string.remove_favourite);
                        }
                    });

                }

                //finish();
            }
        });


    }

    private void checkFavourite() {


        MovieViewModelFactory factory = new MovieViewModelFactory(mDb, movie.getId());

        final MovieViewModel viewModel
                = ViewModelProviders.of(this, factory).get(MovieViewModel.class);


        viewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie favMovie) {
                viewModel.getMovieLiveData().removeObserver(this);

                if(favMovie != null){
                    favouriteMovie = true;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            btFavourite.setText(R.string.remove_favourite);
                        }
                    });

                }else {
                    favouriteMovie = false;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            btFavourite.setText(R.string.add_favourite);
                        }
                    });

                }

            }
        });


    }

    public void fetchTrailers(){


        TMDBMoviesService.getTrailersByMovieId( movie.getId(), Config.TMDB_API_KEY, new Callback<TMDBVideoResponse>() {
            @Override
            public void onResponse(Call<TMDBVideoResponse> call, Response<TMDBVideoResponse> response) {
                Log.e(TAG, call.request().toString());

                Log.e(TAG, ReflectionToStringBuilder.toString(response));

                mTrailerList = response.body().getResults();
                trailerAdapter.setTrailerList(mTrailerList);

            }

            @Override
            public void onFailure(Call<TMDBVideoResponse> call, Throwable t) {

                Log.e(TAG, "ERROR  " +t.toString());
            }
        });


    }

    public void fetchReviews(){

        TMDBMoviesService.getReviewsByMovieId(movie.getId(),Config.TMDB_API_KEY, currentPage, new Callback<TMDBReviewResponse>() {
            @Override
            public void onResponse(Call<TMDBReviewResponse> call, Response<TMDBReviewResponse> response) {
                Log.e(TAG, call.request().toString());

                Log.e(TAG, ReflectionToStringBuilder.toString(response));
                totalPages = response.body().getTotalPages();
                mReviews.addAll(response.body().getResults());

                reviewAdapter.setReviewList(mReviews);

            }

            @Override
            public void onFailure(Call<TMDBReviewResponse> call, Throwable t) {

                Log.e(TAG, "ERROR  " +t.toString());
            }
        });

    }


    @Override
    public void onClickListener(Review review) {

        Intent intent = new Intent(this, ReviewDetails.class);
        intent.putExtra(LOAD_REVIEW_OBJECT, review);

        startActivity(intent);

    }

    @Override
    public void onTrailerClickListener(Trailer trailer) {

        if (mTrailerList != null) {
            Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL+trailer.getKey()));
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Trailer Unavailable please try again", Toast.LENGTH_SHORT).show();
            fetchTrailers();
        }


    }
}
