package com.example.moviebase.ui.main.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.example.moviebase.R;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClick;
import com.example.moviebase.data.model.api.DataResponse;
import com.example.moviebase.data.model.Movie;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.ui.main.movie_details.MovieDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends ViewModel implements OnMovieItemClick {

    private DataRepository dataRepository;
    private MutableLiveData< List<Movie> > moviesList;;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Integer> totalMoviesPages;

    @Inject
    public MoviesViewModel(DataRepository dataRepository){
        this.dataRepository = dataRepository;
        moviesList = new MutableLiveData<>();
        totalMoviesPages = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData< Integer > getTotalMoviesPages() {
        return totalMoviesPages;
    }

    public void getMoviesListApiCall(String category , int page){
         dataRepository.getMoviesList(category,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver< DataResponse >() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                    @Override
                    public void onSuccess(DataResponse dataResponse) {
                        moviesList.setValue(dataResponse.getMovies());
                        totalMoviesPages.setValue(dataResponse.getTotalPages());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , "Observe " +  e.getMessage());
                    }
             });
    }

    public LiveData<List<Movie>> getFavoriteMoviesList(){
       //TODO: observe 2 different lists from outside (Try to observe same movies list);
       return dataRepository.getFavoriteMovies();
    }

    public MutableLiveData< List<Movie>> getMoviesList() {
        return moviesList;
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
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
