package com.example.moviebase.remote;

import com.example.moviebase.models.DataResponse;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReviewResponse;
import com.example.moviebase.models.MovieVideosResponse;

import io.reactivex.Single;

public interface ApiHelper {
    Single< DataResponse > getMoviesList(String category , int page);
    Single< MovieDetails > getMovieDetailsData(int movieID);
    Single<DataResponse> getSimilarMoviesListData(int movieID , int page);
    Single< MovieReviewResponse > getMovieReviewsListData(int movieID, int page);
    Single< MovieVideosResponse > getMovieTrailersListData(final int movieID);
}
