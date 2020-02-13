package com.example.moviebase.dagger.builders;

import com.example.moviebase.views.MainActivity;
import com.example.moviebase.views.MovieDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesBuilderModule {

    @ContributesAndroidInjector
    abstract MovieDetailsActivity contributeMovieDetailsActivity();

    @ContributesAndroidInjector (modules = FragmentsBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
