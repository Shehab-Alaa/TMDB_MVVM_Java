package com.example.moviebase.data;


import com.example.moviebase.data.remote.AppApiHelper;
import com.example.moviebase.data.local.db.AppDbHelper;
import com.example.moviebase.data.model.api.DataResponse;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.data.model.MovieDetails;

import com.example.moviebase.data.model.api.MovieReviewResponse;
import com.example.moviebase.data.model.api.MovieVideosResponse;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import io.reactivex.Single;


@Singleton
public class DataRepository implements DataRepoHelper{

    //TODO:: afsel Repos;

    private AppApiHelper appApiHelper;
    private AppDbHelper appDbHelper;

    @Inject
    public DataRepository(AppApiHelper appApiHelper , AppDbHelper appDbHelper){
        this.appApiHelper = appApiHelper;
        this.appDbHelper = appDbHelper;
    }

    @Override
    public Single< DataResponse > getMoviesList(String category, int page) {
        return appApiHelper.getMoviesList(category , page);
    }

    @Override
    public Single< MovieDetails > getMovieDetailsData(int movieID) {
        return appApiHelper.getMovieDetailsData(movieID);
    }

    @Override
    public Single< DataResponse > getSimilarMoviesListData(int movieID, int page) {
        return appApiHelper.getSimilarMoviesListData(movieID ,page);
    }

    @Override
    public Single< MovieReviewResponse > getMovieReviewsListData(int movieID, int page) {
        return appApiHelper.getMovieReviewsListData(movieID, page);
    }

    @Override
    public Single<MovieVideosResponse> getMovieTrailersListData(int movieID) {
        return appApiHelper.getMovieTrailersListData(movieID);
    }


    @Override
    public void addFavoriteMovie(Movie movie) {
        appDbHelper.addFavoriteMovie(movie);
    }

    @Override
    public LiveData< List< Movie > > getFavoriteMovies() {
        return appDbHelper.getFavoriteMovies();
    }

    @Override
    public void removeFavoriteMovie(long movieID) {
         appDbHelper.removeFavoriteMovie(movieID);
    }

    @Override
    public Single<Integer> isFavoriteMovie(long movieID) {
        return appDbHelper.isFavoriteMovie(movieID);
    }

    @Override
    public int getRowsCount() {
        return appDbHelper.getRowsCount();
    }
}


