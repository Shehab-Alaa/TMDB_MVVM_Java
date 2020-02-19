package com.example.moviebase.ui.main.favorite;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.example.moviebase.R;
import com.example.moviebase.data.DataRepository;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.ui.main.movie_details.MovieDetailsActivity;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


public class FavoriteMoviesViewModel extends ViewModel implements OnMovieItemClickListener {

    private LiveData< List< Movie > > favoriteMoviesList;;

    @Inject
    public FavoriteMoviesViewModel(DataRepository dataRepository){
        favoriteMoviesList = dataRepository.getFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMoviesList(){
        return favoriteMoviesList;
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
