package com.example.moviebase.viewmodels;

import android.content.Context;
import android.util.Log;

import com.example.moviebase.models.Movie;
import com.example.moviebase.repositories.DataRepository;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoviesViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<ArrayList< Movie>> moviesList;;

    public void init(){
        if (dataRepository == null){
            dataRepository = DataRepository.getInstance();
        }
    }

    public void getMoviesData(String category , int page){
        moviesList = dataRepository.getMoviesList(category, page);
    }

    public MutableLiveData< ArrayList<Movie>> getMoviesList() {
        return moviesList;
    }
}
