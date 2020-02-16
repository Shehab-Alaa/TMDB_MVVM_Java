package com.example.moviebase.utils.eventhandlers;

import android.view.View;

import com.example.moviebase.data.model.Movie;

public interface OnMovieItemClick {
    void onMovieItemClick(View view , Movie movie);
}
