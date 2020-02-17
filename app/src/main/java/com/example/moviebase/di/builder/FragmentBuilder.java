package com.example.moviebase.di.builder;

import com.example.moviebase.ui.main.favorite.FavoriteMoviesFragment;
import com.example.moviebase.ui.main.movie.MoviesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract MoviesFragment contributeMoviesFragment();

    @ContributesAndroidInjector
    abstract FavoriteMoviesFragment contributeFavoriteMoviesFragment();
}
