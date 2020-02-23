package com.example.moviebase.data.remote;

import com.example.moviebase.data.model.api.DataResponse;
import com.example.moviebase.data.model.MovieDetails;
import com.example.moviebase.data.model.api.MovieReviewResponse;
import com.example.moviebase.data.model.api.MovieVideosResponse;
import com.example.moviebase.data.remote.client.ApiClient;
import com.example.moviebase.data.remote.client.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ApiRepository implements ApiDataSource {

    private ApiService apiService;

    @Inject
    public ApiRepository(ApiService apiService){
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
