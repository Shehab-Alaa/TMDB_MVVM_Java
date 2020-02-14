package com.example.moviebase.database;

import com.example.moviebase.models.Movie;

import java.util.List;


import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbHelper {
    Observable<Boolean> addFavoriteMovie(Movie movie);
    LiveData< List<Movie> > getFavoriteMovies();
    Observable<Boolean> removeFavoriteMovie(long movieID);
    Single<Integer> isFavoriteMovie(long movieID);
}
