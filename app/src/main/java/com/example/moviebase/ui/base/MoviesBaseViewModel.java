package com.example.moviebase.ui.base;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.data.model.Movie;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MoviesBaseViewModel extends BaseViewModel {

    private MutableLiveData< List< Movie > > moviesList;
    private CompositeDisposable compositeDisposable;

    public MoviesBaseViewModel(DataRepository dataRepository) {
        super(dataRepository);
        moviesList = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public void addCompositeDisposable(Disposable disposable)
    {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public MutableLiveData< List< Movie > > getMoviesList() {
        return moviesList;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
