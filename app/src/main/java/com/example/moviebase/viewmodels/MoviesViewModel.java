package com.example.moviebase.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.example.moviebase.R;
import com.example.moviebase.databinding.eventhandlers.OnMovieItemClick;
import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.repositories.DataRepository;
import com.example.moviebase.views.MovieDetailsActivity;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Interceptor;

public class MoviesViewModel extends ViewModel implements OnMovieItemClick {

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieItemClick(View itemView, Movie movie) {
        Intent intent = new Intent(itemView.getContext() , MovieDetailsActivity.class);
        intent.putExtra("SelectedMovie" , movie);

        // set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((Activity) itemView.getContext(),
                        itemView.findViewById(R.id.movie_poster),
                        ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster)));
        itemView.getContext().startActivity(intent , options.toBundle());
    }
}
