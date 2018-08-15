package com.olamide.latestmovies.network;

import com.olamide.latestmovies.bean.TMDBMovieResponse;
import com.olamide.latestmovies.bean.TMDBReviewResponse;
import com.olamide.latestmovies.bean.TMDBVideoResponse;

import retrofit2.Callback;

public class TMDBMoviesService {


    public static void getMoviesByTopRated(String apiKey,int page, Callback<TMDBMovieResponse> movieResponseCallback){
        RetrofitBuilder.getMoviesApi().getMoviesByTopRated(apiKey,  page).enqueue(movieResponseCallback);
    }

    public static void getMoviesBypopularity(String apiKey, int page, Callback<TMDBMovieResponse> callBack){
        RetrofitBuilder.getMoviesApi().getMoviesBypopularity(apiKey, page).enqueue(callBack);
    }


    public static void getTrailersByMovieId(String id, String apiKey, int page, Callback<TMDBVideoResponse> callBack){
        RetrofitBuilder.getMoviesApi().getTrailersByMovieId(id, apiKey, page).enqueue(callBack);
    }

    public static void getReviewsByMovieId(String id, String apiKey, int page, Callback<TMDBReviewResponse> callBack) {
        RetrofitBuilder.getMoviesApi().getReviewsByMovieId(id, apiKey, page).enqueue(callBack);

    }


}
