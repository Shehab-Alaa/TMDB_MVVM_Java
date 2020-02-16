package com.example.moviebase.di.builder;

import com.example.moviebase.ui.main.MainActivity;
import com.example.moviebase.ui.main.movie_details.MovieDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MovieDetailsActivity contributeMovieDetailsActivity();

    @ContributesAndroidInjector (modules = FragmentBuilder.class)
    abstract MainActivity contributeMainActivity();
}
