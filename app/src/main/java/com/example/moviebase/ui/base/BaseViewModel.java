package com.example.moviebase.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.example.moviebase.R;
import com.example.moviebase.data.DataRepository;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.ui.main.movie_details.MovieDetailsActivity;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;

import java.util.Objects;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel implements OnMovieItemClickListener {

    // TODO:: BaseActivity;
    private DataRepository dataRepository;
    private MutableLiveData<Boolean> isLoading;

    public BaseViewModel(DataRepository dataRepository){
        this.dataRepository = dataRepository;
        isLoading = new MutableLiveData<>(true);
    }

    public MutableLiveData< Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    // TODO :: BaseViewModel Not Depend on this onClick;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieItemClick(View itemView, Movie movie) {
        Intent intent = new Intent(itemView.getContext() , MovieDetailsActivity.class);
        intent.putExtra(AppConstants.SELECTED_MOVIE, movie);

        // set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((Activity) itemView.getContext(),
                        itemView.findViewById(R.id.movie_poster),
                        Objects.requireNonNull(ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster))));
        itemView.getContext().startActivity(intent , options.toBundle());
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }

}
