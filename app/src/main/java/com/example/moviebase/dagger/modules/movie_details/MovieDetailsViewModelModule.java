package com.example.moviebase.dagger.modules.movie_details;

import com.example.moviebase.dagger.ViewModelKey;
import com.example.moviebase.viewmodels.MovieDetailsViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MovieDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel bindMovieDetailsViewModel(MovieDetailsViewModel movieDetailsViewModel);
}
