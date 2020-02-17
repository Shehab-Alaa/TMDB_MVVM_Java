package com.example.moviebase.ui.main.movie;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesFragmentModule {

    @Provides
    MoviesAdapter provideMoviesAdapter(){
        return new MoviesAdapter(new ArrayList<>());
    }
}
