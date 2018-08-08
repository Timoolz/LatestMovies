package com.olamide.latestmovies.network;

import com.olamide.latestmovies.bean.TMDBMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBMoviesAPI {


    @GET("movie/top_rated")
    Call<TMDBMovieResponse> getMoviesByTopRated(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<TMDBMovieResponse> getMoviesBypopularity(@Query("api_key") String apiKey, @Query("page") int page);

}
