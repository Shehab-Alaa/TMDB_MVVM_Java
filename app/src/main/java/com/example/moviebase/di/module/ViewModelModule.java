package com.example.moviebase.di.module;

import com.example.moviebase.di.ViewModelKey;
import com.example.moviebase.ui.main.MainViewModel;
import com.example.moviebase.ui.main.favorite.FavoriteMoviesViewModel;
import com.example.moviebase.ui.main.movie_details.MovieDetailsViewModel;
import com.example.moviebase.ui.main.movie.MoviesViewModel;
import com.example.moviebase.ViewModelProviderFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMoviesViewModel.class)
    abstract ViewModel bindFavoriteMoviesViewModel(FavoriteMoviesViewModel favoriteMoviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindMoviesViewModel(MoviesViewModel moviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel bindMovieDetailsViewModel(MovieDetailsViewModel movieDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
