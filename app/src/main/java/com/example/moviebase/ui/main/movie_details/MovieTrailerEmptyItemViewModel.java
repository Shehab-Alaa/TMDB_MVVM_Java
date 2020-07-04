package com.example.moviebase.ui.main.movie_details;

import com.example.moviebase.ui.base.BaseEmptyItemListener;

public class MovieTrailerEmptyItemViewModel {

    private MovieTrailerEmptyItemListener movieTrailerEmptyItemListener;

    public MovieTrailerEmptyItemViewModel(MovieTrailerEmptyItemListener movieTrailerEmptyItemListener){
        this.movieTrailerEmptyItemListener = movieTrailerEmptyItemListener;
    }

    public void onRetryClick(){
        movieTrailerEmptyItemListener.onRetryClick();
    }

    public interface MovieTrailerEmptyItemListener extends BaseEmptyItemListener {

    }
}
