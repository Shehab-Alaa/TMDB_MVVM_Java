package com.example.moviebase.data.local.db;

import com.example.moviebase.data.model.Movie;
import com.example.moviebase.data.local.db.dao.MoviesDAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class},version = 1,exportSchema = false)
abstract public class AppDatabase extends RoomDatabase {
    public abstract MoviesDAO getMoviesDAO();
}
