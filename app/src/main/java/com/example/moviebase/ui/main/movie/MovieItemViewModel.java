package com.example.moviebase.ui.main.movie;


import android.view.View;

import com.example.moviebase.data.model.Movie;
import com.example.moviebase.ui.base.BaseItemListener;

import androidx.databinding.ObservableField;


public class MovieItemViewModel{

      private final Movie movie;
      private final MovieItemClickListener movieItemClickListener;
      public final ObservableField<String> moviePoster;
      public final ObservableField<String> movieTitle;


      public MovieItemViewModel(Movie movie,MovieItemClickListener movieItemClickListener){
          this.movie = movie;
          this.movieItemClickListener = movieItemClickListener;
          moviePoster = new ObservableField<>(movie.getPosterPath());
          movieTitle = new ObservableField<>(movie.getOriginalTitle());
      }

      public void onItemClick(View view){
          movieItemClickListener.onItemClick(view,movie);
      }

      public interface MovieItemClickListener extends BaseItemListener<Movie>{
          // to be implemented by the adapter.
      }
}
