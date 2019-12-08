package com.example.moviebase.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.moviebase.clients.ApiClient;
import com.example.moviebase.clients.ApiService;
import com.example.moviebase.databinding.MyApplication;
import com.example.moviebase.databinding.components.ApplicationComponent;
import com.example.moviebase.databinding.components.DaggerApplicationComponent;
import com.example.moviebase.databinding.qualifiers.ApplicationContextQualifier;
import com.example.moviebase.models.DataResponse;
import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReview;
import com.example.moviebase.models.MovieReviewResponse;
import com.example.moviebase.models.MovieTrailer;
import com.example.moviebase.models.MovieVideosResponse;
import com.example.moviebase.views.MainActivity;


import java.util.ArrayList;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static DataRepository dataRepository;
    private MutableLiveData<ArrayList<Movie>> moviesList;
    private MutableLiveData<MovieDetails> movieDetails;
    private MutableLiveData<ArrayList<Movie>> similarMoviesList;
    private MutableLiveData<ArrayList<MovieReview>> movieReviewsList;
    private MutableLiveData<ArrayList<MovieTrailer>> movieTrailersList;


    @Inject
    ApiService apiService;

    public static DataRepository getInstance(){
        if (dataRepository == null){
            dataRepository = new DataRepository();
            MyApplication.getApplicationComponent().injectRepository(dataRepository);
        }
        return dataRepository;
    }

    public MutableLiveData<ArrayList<Movie>> getMoviesList(String category , int page){

        moviesList = new MutableLiveData<>();

        final Call< DataResponse > moviesCall = apiService
                .getMovies(
                        category ,
                        ApiClient.API_KEY ,

                        ApiClient.LANGUAGE ,
                        page);

        moviesCall.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                // when data is ready the data will be posted and who is observe will update UI
                if (response.isSuccessful()){
                    moviesList.postValue((ArrayList< Movie >) response.body().getMovies());
                }else{
                    moviesList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                moviesList.postValue(null);
                Log.e("API Service Presenter >"  , " error in getting data from API");
                Log.e("error message > " , t.getMessage());
            }
        });

        return moviesList;
    }

    public MutableLiveData<MovieDetails> getMovieDetailsData(int movieID){

        movieDetails = new MutableLiveData<>();

            final Call<MovieDetails> movieDetailsCall = apiService
                           .getMovieDetails(
                            movieID ,
                            ApiClient.API_KEY ,
                            ApiClient.LANGUAGE
                    );
            movieDetailsCall.enqueue(new Callback<MovieDetails>() {
                @Override
                public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                    if (response.isSuccessful())
                        movieDetails.postValue(response.body());
                    else
                        movieDetails.postValue(null);
                }

                @Override
                public void onFailure(Call<MovieDetails> call, Throwable t) {
                    Log.e("API Service Presenter >"  , " error in getting data from API");
                    Log.e("error message > " , t.getMessage());
                }
            });
        return movieDetails;
     }

    public MutableLiveData<ArrayList<Movie>> getSimilarMoviesListData(int movieID , int page){
        similarMoviesList = new MutableLiveData<>();

        final Call<DataResponse> moviesCall = apiService
                        .getSimilarMovies(
                        movieID ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE ,
                        page);

        moviesCall.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful())
                    similarMoviesList.postValue((ArrayList<Movie>) response.body().getMovies());
                else
                    similarMoviesList.postValue(null);
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("API Service Presenter >"  , " error in getting data from API");
                Log.e("error message > " , t.getMessage());
            }
        });

        return similarMoviesList;
    }

    public MutableLiveData<ArrayList<MovieReview>> getMovieReviewsListData(int movieID,int page){

        movieReviewsList = new MutableLiveData<>();

        final Call<MovieReviewResponse> movieReviewResponseCall = apiService
                        .getMovieReviews(
                        movieID ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE ,
                        page);

        movieReviewResponseCall.enqueue(new Callback< MovieReviewResponse >() {
            @Override
            public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
                if (response.isSuccessful())
                    movieReviewsList.postValue((ArrayList<MovieReview>) response.body().getMovieReviews());
                else
                    movieReviewsList.postValue(null);
            }

            @Override
            public void onFailure(Call<MovieReviewResponse> call, Throwable t) {
                Log.e("API Service Presenter >"  , " error in getting data from API");
                Log.e("error message > " , t.getMessage());
            }
        });

        return movieReviewsList;
    }

    public MutableLiveData<ArrayList<MovieTrailer>> getMovieTrailersListData(final int movieID){
        movieTrailersList = new MutableLiveData<>();

        final Call<MovieVideosResponse> movieVideosResponseCall = apiService
                .getMovieTrailers(
                        movieID ,
                        ApiClient.API_KEY ,
                        ApiClient.LANGUAGE);
        movieVideosResponseCall.enqueue(new Callback< MovieVideosResponse >() {
            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getMovieTrailers().size() > 0)
                        movieTrailersList.postValue((ArrayList<MovieTrailer>) response.body().getMovieTrailers());
                }else{
                    movieTrailersList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
                Log.e("API Service Presenter >"  , " error in getting data from API");
                Log.e("error message > " , t.getMessage());
            }
        });
        return movieTrailersList;
    }

}


