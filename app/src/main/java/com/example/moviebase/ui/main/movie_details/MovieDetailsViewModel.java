package com.example.moviebase.ui.main.movie_details;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.example.moviebase.R;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.eventhandlers.OnFavoriteBtnClickListener;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;
import com.example.moviebase.data.model.api.DataResponse;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.data.model.MovieDetails;
import com.example.moviebase.data.model.MovieReview;
import com.example.moviebase.data.model.api.MovieReviewResponse;
import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.data.model.api.MovieVideosResponse;
import com.example.moviebase.data.DataRepository;
import com.example.moviebase.utils.eventhandlers.OnMovieTrailerClickListener;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsViewModel extends ViewModel implements OnMovieItemClickListener, OnFavoriteBtnClickListener , OnMovieTrailerClickListener {

    private DataRepository dataRepository;
    private MutableLiveData<MovieDetails> movieDetails;
    private MutableLiveData<ArrayList<Movie>> similarMoviesList;
    private MutableLiveData<ArrayList<MovieTrailer>> movieTrailersList;
    private MutableLiveData<ArrayList<MovieReview>> movieReviewsList;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Boolean> isFavorite;

    @Inject
    public MovieDetailsViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        movieDetails = new MutableLiveData<>();
        similarMoviesList = new MutableLiveData<>();
        movieTrailersList = new MutableLiveData<>();
        movieReviewsList = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        isFavorite = new MutableLiveData<>();
    }

    public void getMovieDetailsData(int movieID) {
        dataRepository.getMovieDetailsData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< MovieDetails >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
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
        dataRepository.getSimilarMoviesListData(movieID , page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< DataResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        similarMoviesList.setValue((ArrayList< Movie >) dataResponse.getMovies());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , e.getMessage());
                    }
                });

    }

    public void getMovieTrailersListData(int movieID) {
        dataRepository.getMovieTrailersListData(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< MovieVideosResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
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
        dataRepository.getMovieReviewsListData(movieID,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< MovieReviewResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
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

    public MutableLiveData< ArrayList< Movie > > getSimilarMoviesList() {
        return similarMoviesList;
    }

    public MutableLiveData< ArrayList< MovieTrailer > > getMovieTrailersList() {
        return movieTrailersList;
    }

    public MutableLiveData< ArrayList< MovieReview > > getMovieReviewsList() {
        return movieReviewsList;
    }

    public void checkFavoriteMovies(long movieID){
        dataRepository.isFavoriteMovie(movieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< Integer >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieItemClick(View itemView, Movie movie) {
        Intent intent = new Intent(itemView.getContext() , MovieDetailsActivity.class);
        intent.putExtra(AppConstants.SELECTED_MOVIE, movie);

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
        if (isFavorite.getValue()){
            dataRepository.removeFavoriteMovie(movie.getId());
            isFavorite.setValue(false);
        }else{
            dataRepository.addFavoriteMovie(movie);
            isFavorite.setValue(true);
        }
        int counter = dataRepository.getRowsCount();
    }

    public MutableLiveData< Boolean > getIsFavorite() {
        return isFavorite;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
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
