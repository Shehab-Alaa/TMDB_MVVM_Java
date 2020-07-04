package com.example.moviebase.ui.main.favorite;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;


public class FavoriteMoviesViewModel extends BaseViewModel {

    private LiveData<List< Movie >> favoriteMoviesList;

    @Inject
    public FavoriteMoviesViewModel(DataRepository dataRepository){
        super(dataRepository);
        setIsLoading(true);
        favoriteMoviesList = dataRepository.getDatabaseRepository().getFavoriteMovies();
        setIsLoading(false);
    }

    public LiveData< List< Movie > > getFavoriteMoviesList() {
        return favoriteMoviesList;
    }
}
