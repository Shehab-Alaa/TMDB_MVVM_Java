package com.example.moviebase.ui.main.movie;


import android.util.Log;

import com.example.moviebase.data.model.api.DataResponse;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.ui.base.MoviesBaseViewModel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends MoviesBaseViewModel {

    private MutableLiveData<Integer> totalMoviesPages;

    @Inject
    public MoviesViewModel(DataRepository dataRepository){
        super(dataRepository);
        totalMoviesPages = new MutableLiveData<>();
    }

    public MutableLiveData< Integer > getTotalMoviesPages() {
        return totalMoviesPages;
    }

    public void getMoviesListApiCall(String category , int page){
         getDataRepository().getApiRepository().getMoviesList(category,page)
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
                        totalMoviesPages.setValue(dataResponse.getTotalPages());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Here" , "Observe " +  e.getMessage());
                    }
             });
    }

}
