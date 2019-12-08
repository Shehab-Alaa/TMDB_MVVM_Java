package com.example.moviebase.databinding.modules;

import com.example.moviebase.adapters.MoviesAdapter;
import com.example.moviebase.models.Movie;
import com.example.moviebase.databinding.scopes.ActivityScope;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

    @Provides
    @ActivityScope
    MoviesAdapter getMoviesAdapter(){
        return new MoviesAdapter(new ArrayList< Movie >());
    }
}
