package com.example.moviebase.dagger.modules;

import com.example.moviebase.dagger.ViewModelKey;
import com.example.moviebase.viewmodels.MovieDetailsViewModel;
import com.example.moviebase.viewmodels.MoviesViewModel;
import com.example.moviebase.viewmodels.ViewModelProviderFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel bindMovieDetailsViewModel(MovieDetailsViewModel movieDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
