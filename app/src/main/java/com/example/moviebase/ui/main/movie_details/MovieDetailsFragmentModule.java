package com.example.moviebase.ui.main.movie_details;

import com.example.moviebase.ui.main.movie.MoviesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailsFragmentModule {

    @Provides
    MoviesAdapter provideSimilarMoviesAdapter(){
        return new MoviesAdapter(new ArrayList<>());
    }

    @Provides
    MovieReviewsAdapter provideMovieReviewsAdapter(){
        return new MovieReviewsAdapter(new ArrayList<>());
    }

    @Provides
    MovieTrailersAdapter provideMovieTrailersAdapter(){
        return new MovieTrailersAdapter(new ArrayList<>());
    }

}
