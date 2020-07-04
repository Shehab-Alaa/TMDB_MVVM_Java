package com.example.moviebase.data.local.db;

import com.example.moviebase.data.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public interface DbDataSource {
    void addFavoriteMovie(Movie movie);
    LiveData< List<Movie> > getFavoriteMovies();
    void removeFavoriteMovie(long movieID);
    Single<Integer> isFavoriteMovie(long movieID);
    int getRowsCount();
}
