package com.example.moviebase.database;



import com.example.moviebase.models.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import io.reactivex.Single;


@Singleton
public class AppDbHelper implements DbHelper {

    private AppDatabase appDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }


    @Override
    public Observable< Boolean > addFavoriteMovie(Movie movie) {
        return Observable.fromCallable(()-> {
            appDatabase.getMoviesDAO().insert(movie);
            return true;
        });
    }

    @Override
    public LiveData< List< Movie > > getFavoriteMovies() {
        return appDatabase.getMoviesDAO().loadAll();
    }

    @Override
    public Observable< Boolean > removeFavoriteMovie(long movieID) {
        return io.reactivex.Observable.fromCallable(()-> {
            appDatabase.getMoviesDAO().delete(movieID);
            return true;
        });
    }

    @Override
    public Single< Integer > isFavoriteMovie(long movieID) {
        return appDatabase.getMoviesDAO().isExist(movieID);
    }
}
