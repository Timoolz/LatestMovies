package com.olamide.latestmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {

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

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        movie =  getIntent().getParcelableExtra(MainActivity.VIEW_MOVIE_OBJECT);
        populateMovie();

    }

    private void populateMovie(){

        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getReleaseDate());
        tvRatings.setText(Double.toString(movie.getVoteAverage()) + "/10");
        tvSynopsis.setText(movie.getOverview());

        String image_url = Config.TMDB_IMAGE_URL_PATH +movie.getPosterPath();
        Picasso.with(this)
                .load(image_url)
                .placeholder(android.R.mipmap.sym_def_app_icon)
                .error(android.R.mipmap.sym_def_app_icon)
                .into(ivPoster);


    }
}
