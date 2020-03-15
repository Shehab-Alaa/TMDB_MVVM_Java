package com.example.moviebase.ui.main.favorite;

import android.view.View;

import com.example.moviebase.data.model.Movie;
import com.example.moviebase.ui.base.BaseItemListener;

import androidx.databinding.ObservableField;

public class FavoriteItemViewModel {

    private final Movie movie;
    private final FavoriteMovieItemClickListener favoriteMovieItemClickListener;
    public final ObservableField<String> moviePoster;
    public final ObservableField<String> movieTitle;


    public FavoriteItemViewModel(Movie movie, FavoriteMovieItemClickListener favoriteMovieItemClickListener){
        this.movie = movie;
        this.favoriteMovieItemClickListener = favoriteMovieItemClickListener;
        moviePoster = new ObservableField<>(movie.getPosterPath());
        movieTitle = new ObservableField<>(movie.getOriginalTitle());
    }

    public void onItemClick(View view){
        favoriteMovieItemClickListener.onItemClick(view,movie);
    }

    public interface FavoriteMovieItemClickListener extends BaseItemListener< Movie > {
        // to be implemented by the adapter.
    }
}
