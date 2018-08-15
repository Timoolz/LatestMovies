package com.olamide.latestmovies.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.olamide.latestmovies.bean.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)

public abstract class LatestMoviesDatabase extends RoomDatabase {

    private static final String LOG_TAG = LatestMoviesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "LatestMovies";
    private static LatestMoviesDatabase sInstance;

    public static LatestMoviesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LatestMoviesDatabase.class, LatestMoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }
    public abstract MovieDao movieDao();


}
