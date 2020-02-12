package com.example.moviebase.repositories;

import android.util.Log;

import com.example.moviebase.clients.ApiClient;
import com.example.moviebase.clients.ApiService;
import com.example.moviebase.models.DataResponse;
import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReview;
import com.example.moviebase.models.MovieReviewResponse;
import com.example.moviebase.models.MovieTrailer;
import com.example.moviebase.models.MovieVideosResponse;


import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class DataRepository {

    private ApiService apiService;

    @Inject
    public void dataRepository(ApiService apiService){
        this.apiService = apiService;
    }

    public Single<DataResponse> getMoviesList(String category , int page){
        return apiService.getMovies(
                        category,
                        ApiClient.API_KEY,
                        ApiClient.LANGUAGE,
                        page);
    }

    public Single<MovieDetails> getMovieDetailsData(int movieID){
       return apiService.getMovieDetails(
                            movieID ,
                            ApiClient.API_KEY ,
                            ApiClient.LANGUAGE);
     }

    public Single<DataResponse> getSimilarMoviesListData(int movieID , int page){
        return apiService.getSimilarMovies(
                        movieID ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE ,
                        page);
    }

    public Single<MovieReviewResponse> getMovieReviewsListData(int movieID,int page){
        return apiService.getMovieReviews(
                        movieID ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE ,
                        page);
    }

    public Single<MovieVideosResponse> getMovieTrailersListData(final int movieID){
        return apiService.getMovieTrailers(
                        movieID ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE);
    }

}


