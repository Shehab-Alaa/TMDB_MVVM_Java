package com.example.moviebase.database;

import com.example.moviebase.models.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class},version = 1)
abstract public class FavoriteMoviesRoomDB extends RoomDatabase {
    public abstract MoviesDAO getMoviesDAO();
}
