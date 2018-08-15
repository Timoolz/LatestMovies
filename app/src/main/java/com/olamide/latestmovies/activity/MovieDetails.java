package com.olamide.latestmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.olamide.latestmovies.Config;
import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.bt_favourite)
    Button btFavourite;

    private Movie movie;

    String favouriteText;
    boolean favouriteMovie = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);



        if(favouriteMovie){
            favouriteText = this.getResources().getString(R.string.remove_favourite);
        }else {
            favouriteText = this.getResources().getString(R.string.add_favourite);
        }

        movie =  getIntent().getParcelableExtra(MainActivity.VIEW_MOVIE_OBJECT);
        populateMovie();



    }

    private void populateMovie(){

        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getReleaseDate().substring(0,4));
        tvRatings.setText(Double.toString(movie.getVoteAverage()) + "/10");
        tvSynopsis.setText(movie.getOverview());
        btFavourite.setText(favouriteText);

        String image_url = Config.TMDB_IMAGE_URL_PATH +movie.getPosterPath();
        Picasso.with(this)
                .load(image_url)
                .placeholder(R.drawable.loader)
                .error(R.drawable.errorr)
                .into(ivPoster);


    }

    @OnClick(R.id.bt_favourite)
    public void setFavourite(){

        if(favouriteMovie){
            Toast.makeText(MovieDetails.this, "Successfully removed this movie from favourite  " ,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MovieDetails.this, "Successfully added this movie as favourite  " ,Toast.LENGTH_LONG).show();
        }

    }
}
