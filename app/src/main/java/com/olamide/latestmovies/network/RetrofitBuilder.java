package com.olamide.latestmovies.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.olamide.latestmovies.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static Retrofit retrofit;
    private static TMDBMoviesAPI moviesApi = getRetrofitBuilder().create(TMDBMoviesAPI.class);

    private static Retrofit getRetrofitBuilder() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.TMDB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }




    public static TMDBMoviesAPI getMoviesApi() {
        return moviesApi;
    }


}
