package com.olamide.latestmovies.network;

import com.olamide.latestmovies.bean.TMDBMovieResponse;

import retrofit2.Callback;

public class TMDBMoviesService {


    public static void getMoviesByTopRated(String apiKey,int page, Callback<TMDBMovieResponse> movieResponseCallback){
        RetrofitBuilder.getMoviesApi().getMoviesByTopRated(apiKey,  page).enqueue(movieResponseCallback);
    }

    public static void getMoviesBypopularity(String apiKey, int page, Callback<TMDBMovieResponse> callBack){
        RetrofitBuilder.getMoviesApi().getMoviesBypopularity(apiKey, page).enqueue(callBack);
    }


}
