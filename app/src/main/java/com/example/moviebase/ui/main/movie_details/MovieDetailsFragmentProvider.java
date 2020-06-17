package com.example.moviebase.ui.main.movie_details;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieDetailsFragmentProvider {

    @ContributesAndroidInjector(modules = MovieDetailsFragmentModule.class)
    abstract MovieDetailsFragment provideMoviesDetailsFragmentFactory();
}
