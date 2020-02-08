package com.example.moviebase.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.example.moviebase.R;
import com.example.moviebase.databinding.eventhandlers.OnExpandCollapseBtnClick;
import com.example.moviebase.databinding.eventhandlers.OnFavoriteBtnClick;
import com.example.moviebase.databinding.eventhandlers.OnMovieItemClick;
import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReview;
import com.example.moviebase.models.MovieTrailer;
import com.example.moviebase.repositories.DataRepository;
import com.example.moviebase.views.MovieDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel implements OnMovieItemClick , OnFavoriteBtnClick {

    private DataRepository dataRepository;
    private MutableLiveData<MovieDetails> movieDetails;
    private MutableLiveData<ArrayList<Movie>> similarMoviesList;
    private MutableLiveData<ArrayList<MovieTrailer>> movieTrailersList;
    private MutableLiveData<ArrayList<MovieReview>> movieReviewsList;


    @Inject
    public MovieDetailsViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieItemClick(View itemView, Movie movie) {
        Intent intent = new Intent(itemView.getContext() , MovieDetailsActivity.class);
        intent.putExtra("SelectedMovie" , movie);

        // set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((Activity) itemView.getContext(),
                        itemView.findViewById(R.id.movie_poster),
                        ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster)));
        itemView.getContext().startActivity(intent , options.toBundle());
    }

    @Override
    public void onFavoriteBtnClick(View view, Movie movie) {
        //Log.i("Here" , floatingActionButton.getBack + " ");
        Log.i("Here" , view.getBackground() + " ");
    }
}
