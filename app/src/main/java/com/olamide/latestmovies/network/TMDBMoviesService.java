package com.olamide.latestmovies.network;

import com.olamide.latestmovies.bean.TMDBMovieResponse;

import retrofit2.Callback;

public class TMDBMoviesService {


    public static void getMoviesByTopRated(String apiKey, Callback<TMDBMovieResponse> movieResponseCallback){
        RetrofitBuilder.getMoviesApi().getMoviesByTopRated(apiKey).enqueue(movieResponseCallback);
    }

    public static void getMoviesBypopularity(String apiKey, Callback<TMDBMovieResponse> callBack){
        RetrofitBuilder.getMoviesApi().getMoviesBypopularity(apiKey).enqueue(callBack);
    }


}
