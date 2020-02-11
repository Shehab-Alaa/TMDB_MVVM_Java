package com.example.moviebase.dagger.modules.movie;

import com.example.moviebase.dagger.ViewModelKey;
import com.example.moviebase.viewmodels.MoviesViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MoviesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

}
