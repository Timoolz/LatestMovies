package com.olamide.latestmovies.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.olamide.latestmovies.R;
import com.olamide.latestmovies.bean.Review;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewDetails extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @BindView(R.id.tv_author)
    TextView tvAuthor;

    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        ButterKnife.bind(this);

        review =  getIntent().getParcelableExtra(MovieDetails.LOAD_REVIEW_OBJECT);

        tvAuthor.setText(review.getAuthor());

        tvContent.setText(review.getContent());
    }

    @OnClick(R.id.online_review)
    public void viewOnline(){

            Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
            startActivity(intent);

    }
}
