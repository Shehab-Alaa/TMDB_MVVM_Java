package com.example.moviebase.ui.main.movie_details;

import com.example.moviebase.data.model.MovieTrailer;

import androidx.databinding.ObservableField;

public class MovieTrailerItemViewModel {

    private MovieTrailer movieTrailer;
    private MovieTrailerClickListener movieTrailerClickListener;
    public ObservableField<String> movieTrailerThumbnails;

    public MovieTrailerItemViewModel(MovieTrailer movieTrailer , MovieTrailerClickListener movieTrailerClickListener){
        this.movieTrailer = movieTrailer;
        this.movieTrailerClickListener = movieTrailerClickListener;
        movieTrailerThumbnails = new ObservableField<>(movieTrailer.getKey());
    }

    public void onItemClick(){
        movieTrailerClickListener.onMovieTrailerClick(movieTrailer);
    }

    public interface MovieTrailerClickListener{
        void onMovieTrailerClick(MovieTrailer movieTrailer);
    }
}
