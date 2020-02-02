package com.example.moviebase.database;

import com.example.moviebase.models.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MoviesDAO {

    @Insert
    long addFavoriteMovie(Movie movie);

    @Query("select * from FavoriteMovies")
    List<Movie> getFavoriteMovies();

    @Query("delete from FavoriteMovies where id = :movieID")
    int removeFavoriteMovie(long movieID);
}
