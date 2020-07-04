package com.example.moviebase.data.remote;

import com.example.moviebase.data.model.MovieDetails;
import com.example.moviebase.data.model.api.DataResponse;
import com.example.moviebase.data.model.api.MovieReviewResponse;
import com.example.moviebase.data.model.api.MovieVideosResponse;

import io.reactivex.Single;

public interface ApiDataSource {
    Single< DataResponse > getMoviesList(String category, int page);
    Single< MovieDetails > getMovieDetailsData(int movieID);
    Single<DataResponse> getSimilarMoviesListData(int movieID, int page);
    Single< MovieReviewResponse > getMovieReviewsListData(int movieID, int page);
    Single< MovieVideosResponse > getMovieTrailersListData(final int movieID);
}
