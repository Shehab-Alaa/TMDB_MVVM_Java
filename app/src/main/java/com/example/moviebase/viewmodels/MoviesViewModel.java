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

    public void getMoviesData(Context context, String category , int page){
        if (dataRepository == null){
            dataRepository = DataRepository.getInstance();
        }
        moviesList = dataRepository.getMoviesList(context, category, page);
        Log.i("Here" , "Iam From View Model Method");

    }

    public MutableLiveData< ArrayList<Movie>> getMoviesList() {
        return moviesList;
    }
}
