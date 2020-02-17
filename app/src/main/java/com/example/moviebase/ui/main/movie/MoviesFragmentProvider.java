package com.example.moviebase.ui.main.movie;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoviesFragmentProvider {

    @ContributesAndroidInjector (modules = MoviesFragmentModule.class)
    abstract MoviesFragment provideMoviesFragmentFactory();
}
