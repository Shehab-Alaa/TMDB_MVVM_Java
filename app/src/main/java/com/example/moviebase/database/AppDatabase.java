package com.example.moviebase.database;

import com.example.moviebase.models.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class},version = 1,exportSchema = false)
abstract public class AppDatabase extends RoomDatabase {
    public abstract MoviesDAO getMoviesDAO();
}
