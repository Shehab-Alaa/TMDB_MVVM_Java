package com.example.moviebase.ui.main.movie;


import com.example.moviebase.ui.base.BaseEmptyItemListener;

public class MovieEmptyItemViewModel{

    private MovieEmptyItemListener movieEmptyItemListener;

    public MovieEmptyItemViewModel(MovieEmptyItemListener movieEmptyItemListener){
        this.movieEmptyItemListener = movieEmptyItemListener;
    }

    public void onRetryClick(){
        movieEmptyItemListener.onRetryClick();
    }

    public interface MovieEmptyItemListener extends BaseEmptyItemListener {

    }

}
