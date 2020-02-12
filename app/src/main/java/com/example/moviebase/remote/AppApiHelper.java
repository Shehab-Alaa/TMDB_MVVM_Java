package com.example.moviebase.remote;

import com.example.moviebase.models.DataResponse;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReviewResponse;
import com.example.moviebase.models.MovieVideosResponse;
import com.example.moviebase.remote.clients.ApiClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiService apiService;

    @Inject
    public AppApiHelper(ApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public Single<DataResponse> getMoviesList(String category , int page){
        return  apiService.getMovies(
                category,
                ApiClient.API_KEY,
                ApiClient.LANGUAGE,
                page);
    }

    @Override
    public Single<MovieDetails> getMovieDetailsData(int movieID){
        return apiService.getMovieDetails(
                movieID ,
                ApiClient.API_KEY ,
                ApiClient.LANGUAGE);
    }

    @Override
    public Single<DataResponse> getSimilarMoviesListData(int movieID , int page){
        return apiService.getSimilarMovies(
                movieID ,
                ApiClient.API_KEY ,
                ApiClient.LANGUAGE ,
                page);
    }

    @Override
    public Single<MovieReviewResponse> getMovieReviewsListData(int movieID,int page){
        return apiService.getMovieReviews(
                movieID ,
                ApiClient.API_KEY ,
                ApiClient.LANGUAGE ,
                page);
    }

    @Override
    public Single<MovieVideosResponse> getMovieTrailersListData(final int movieID){
        return apiService.getMovieTrailers(
                movieID ,
                ApiClient.API_KEY ,
                ApiClient.LANGUAGE);
    }
}
