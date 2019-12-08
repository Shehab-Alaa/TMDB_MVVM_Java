package com.example.moviebase.viewmodels;

import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReview;
import com.example.moviebase.models.MovieTrailer;
import com.example.moviebase.repositories.DataRepository;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<MovieDetails> movieDetails;
    private MutableLiveData<ArrayList<Movie>> similarMoviesList;
    private MutableLiveData<ArrayList<MovieTrailer>> movieTrailersList;
    private MutableLiveData<ArrayList<MovieReview>> movieReviewsList;

    public void init(){
        if (dataRepository == null){
            dataRepository = DataRepository.getInstance();
        }
    }

    public void getMovieDetailsData(int movieID) {
        movieDetails = dataRepository.getMovieDetailsData(movieID);
    }

    public void getSimilarMoviesListData(int movieID , int page) {
        similarMoviesList = dataRepository.getSimilarMoviesListData(movieID , page);
    }

    public void getMovieTrailersListData(int movieID) {
        movieTrailersList = dataRepository.getMovieTrailersListData(movieID);
    }

    public void getMovieReviewsListData(int movieID , int page) {
        movieReviewsList = dataRepository.getMovieReviewsListData(movieID,page);
    }


    public MutableLiveData< MovieDetails > getMovieDetails() {
        return movieDetails;
    }

    public MutableLiveData< ArrayList< Movie > > getSimilarMoviesList() {
        return similarMoviesList;
    }

    public MutableLiveData< ArrayList< MovieTrailer > > getMovieTrailersList() {
        return movieTrailersList;
    }

    public MutableLiveData< ArrayList< MovieReview > > getMovieReviewsList() {
        return movieReviewsList;
    }

}
