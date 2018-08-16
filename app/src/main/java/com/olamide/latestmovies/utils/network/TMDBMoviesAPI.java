package com.olamide.latestmovies.utils.network;

import com.olamide.latestmovies.bean.TMDBMovieResponse;
import com.olamide.latestmovies.bean.TMDBReviewResponse;
import com.olamide.latestmovies.bean.TMDBVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBMoviesAPI {


    @GET("movie/top_rated")
    Call<TMDBMovieResponse> getMoviesByTopRated(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<TMDBMovieResponse> getMoviesBypopularity(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}/videos")
    Call<TMDBVideoResponse> getTrailersByMovieId(@Path("id") String id, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}/reviews")
    Call<TMDBReviewResponse> getReviewsByMovieId(@Path("id") String id, @Query("api_key") String apiKey, @Query("page") int page);

}
