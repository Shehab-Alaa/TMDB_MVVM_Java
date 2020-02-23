package com.example.moviebase.data.local.db;



import com.example.moviebase.data.model.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;


@Singleton
public class DatabaseRepository implements DbDataSource {

    private AppDatabase appDatabase;

    @Inject
    public DatabaseRepository(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }


    @Override
    public void addFavoriteMovie(Movie movie) {
        appDatabase.getMoviesDAO().insert(movie);
    }

    @Override
    public LiveData< List< Movie > > getFavoriteMovies() {
        return appDatabase.getMoviesDAO().loadAll();
    }

    @Override
    public void removeFavoriteMovie(long movieID) {
        appDatabase.getMoviesDAO().delete(movieID);
    }

    @Override
    public Single< Integer > isFavoriteMovie(long movieID) {
        return appDatabase.getMoviesDAO().isExist(movieID);
    }

    @Override
    public int getRowsCount() {
        return appDatabase.getMoviesDAO().countRows();
    }
}
