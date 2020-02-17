package com.example.moviebase.ui.main.favorite;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteMoviesFragmentModule {

    @Provides
    FavoriteMoviesAdapter provideFavoriteMoviesAdapter(){
        return new FavoriteMoviesAdapter(new ArrayList<>());
    }
}
