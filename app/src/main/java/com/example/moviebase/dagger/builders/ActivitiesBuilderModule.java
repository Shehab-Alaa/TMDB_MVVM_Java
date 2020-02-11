package com.example.moviebase.dagger.builders;

import com.example.moviebase.dagger.modules.movie_details.MovieDetailsViewModelModule;
import com.example.moviebase.views.MovieDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector (modules = MovieDetailsViewModelModule.class)
    abstract MovieDetailsActivity contributeMovieDetailsActivity();
}
