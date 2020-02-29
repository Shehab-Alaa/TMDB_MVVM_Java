package com.example.moviebase.ui.main.movie;

import android.util.Log;

import com.example.moviebase.data.model.api.DataResponse;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.ui.base.MoviesBaseViewModel;
import com.example.moviebase.utils.AppConstants;

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
        Log.i("Here","Create New Movies View Model");
        totalMoviesPages = new MutableLiveData<>();
    }

    public void getMoviesListApiCall(String category , int page){
         Log.i("Here", "Category " + category);
         setIsLoading(true);
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
                        setIsLoading(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        setIsLoading(false);
                    }
             });
    }

    public MutableLiveData< Integer > getTotalMoviesPages() {
        return totalMoviesPages;
    }


}
