package com.example.moviebase.repositories;


import com.example.moviebase.remote.AppApiHelper;
import com.example.moviebase.database.AppDbHelper;
import com.example.moviebase.models.DataResponse;
import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;

import com.example.moviebase.models.MovieReviewResponse;
import com.example.moviebase.models.MovieVideosResponse;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import io.reactivex.Single;


@Singleton
public class DataRepository implements DataRepoHelper{

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
    public Observable< Boolean > addFavoriteMovie(Movie movie) {
       return appDbHelper.addFavoriteMovie(movie);
    }

    @Override
    public LiveData< List< Movie > > getFavoriteMovies() {
        return appDbHelper.getFavoriteMovies();
    }

    @Override
    public Observable< Boolean > removeFavoriteMovie(long movieID) {
        return appDbHelper.removeFavoriteMovie(movieID);
    }
}


