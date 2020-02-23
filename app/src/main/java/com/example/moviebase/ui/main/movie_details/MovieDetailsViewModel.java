package com.example.moviebase.ui.main.movie_details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.data.model.MovieDetails;
import com.example.moviebase.data.model.MovieReview;
import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.data.model.api.DataResponse;
import com.example.moviebase.data.model.api.MovieReviewResponse;
import com.example.moviebase.data.model.api.MovieVideosResponse;
import com.example.moviebase.ui.base.MoviesBaseViewModel;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.eventhandlers.OnFavoriteBtnClickListener;
import com.example.moviebase.utils.eventhandlers.OnMovieTrailerClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsViewModel extends MoviesBaseViewModel implements OnFavoriteBtnClickListener , OnMovieTrailerClickListener {

    private MutableLiveData<MovieDetails> movieDetails;
    private MutableLiveData<ArrayList<MovieTrailer>> movieTrailersList;
    private MutableLiveData<ArrayList<MovieReview>> movieReviewsList;
    private MutableLiveData<Boolean> isFavorite;

    @Inject
    public MovieDetailsViewModel(DataRepository dataRepository) {
        super(dataRepository);
        movieDetails = new MutableLiveData<>();
        movieTrailersList = new MutableLiveData<>();
        movieReviewsList = new MutableLiveData<>();
        isFavorite = new MutableLiveData<>();
    }

    public void getMovieDetailsData(int movieID) {
        getDataRepository().getApiRepository().getMovieDetailsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< MovieDetails >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addCompositeDisposable(d);
                    }
                    @Override
                    public void onSuccess(MovieDetails movieDetailsData) {
                        movieDetails.setValue(movieDetailsData);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , e.getMessage());
                    }
                });

    }

    public void getSimilarMoviesListData(int movieID , int page) {
        getDataRepository().getApiRepository().getSimilarMoviesListData(movieID , page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< DataResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addCompositeDisposable(d);
                    }
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        getMoviesList().setValue(dataResponse.getMovies());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , e.getMessage());
                    }
                });

    }

    public void getMovieTrailersListData(int movieID) {
        getDataRepository().getApiRepository().getMovieTrailersListData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< MovieVideosResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addCompositeDisposable(d);
                    }
                    @Override
                    public void onSuccess(MovieVideosResponse movieVideosResponse) {
                        movieTrailersList.setValue((ArrayList< MovieTrailer >) movieVideosResponse.getMovieTrailers());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , e.getMessage());
                    }
                });
    }

    public void getMovieReviewsListData(int movieID , int page) {
        getDataRepository().getApiRepository().getMovieReviewsListData(movieID,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< MovieReviewResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addCompositeDisposable(d);
                    }
                    @Override
                    public void onSuccess(MovieReviewResponse movieReviewResponse) {
                       movieReviewsList.setValue((ArrayList< MovieReview >) movieReviewResponse.getMovieReviews());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , e.getMessage());
                    }
                });
    }

    public MutableLiveData< MovieDetails > getMovieDetails() {
        return movieDetails;
    }

    public MutableLiveData< ArrayList< MovieTrailer > > getMovieTrailersList() {
        return movieTrailersList;
    }

    public MutableLiveData< ArrayList< MovieReview > > getMovieReviewsList() {
        return movieReviewsList;
    }

    public void checkFavoriteMovies(long movieID){
        getDataRepository().getDatabaseRepository().isFavoriteMovie(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< Integer >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addCompositeDisposable(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer == 0)
                        {
                            isFavorite.setValue(false);
                        }
                        else{
                            isFavorite.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isFavorite.setValue(false);
                    }
                });
    }

    @Override
    public void onFavoriteBtnClick(View view, Movie movie) {
        if (isFavorite.getValue()){
            getDataRepository().getDatabaseRepository().removeFavoriteMovie(movie.getId());
            isFavorite.setValue(false);
        }else{
            getDataRepository().getDatabaseRepository().addFavoriteMovie(movie);
            isFavorite.setValue(true);
        }
    }

    public MutableLiveData< Boolean > getIsFavorite() {
        return isFavorite;
    }

    @Override
    public void onMovieTrailerClick(View view, MovieTrailer movieTrailer) {
        openYoutubeApp(view , movieTrailer.getKey());
    }

    private void openYoutubeApp(View view ,String videoId){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_APP_LINK + videoId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_WEB_LINK + videoId));
        try {
            view.getContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            view.getContext().startActivity(webIntent);
        }
    }

}
