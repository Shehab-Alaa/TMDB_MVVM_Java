package com.example.moviebase.ui.main.favorite;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoriteMoviesFragmentProvider {

    @ContributesAndroidInjector (modules = FavoriteMoviesFragmentModule.class)
    abstract FavoriteMoviesFragment provideFavoriteMoviesFragmentFactory();
}
