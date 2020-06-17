package com.example.moviebase.di.builder;

import com.example.moviebase.ui.main.MainActivity;
import com.example.moviebase.ui.main.favorite.FavoriteMoviesFragmentProvider;
import com.example.moviebase.ui.main.movie.MoviesFragmentProvider;
import com.example.moviebase.ui.main.movie_details.MovieDetailsFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules =
            {FavoriteMoviesFragmentProvider.class
                    , MoviesFragmentProvider.class
                    , MovieDetailsFragmentProvider.class})
    abstract MainActivity contributeMainActivity();
}
