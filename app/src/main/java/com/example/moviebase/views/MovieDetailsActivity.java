package com.example.moviebase.views;

import android.os.Bundle;

import com.example.moviebase.R;
import com.example.moviebase.adapters.MovieReviewsAdapter;
import com.example.moviebase.adapters.MovieTrailersAdapter;
import com.example.moviebase.adapters.MoviesAdapter;
import com.example.moviebase.databinding.ActivityMovieInformationBinding;
import com.example.moviebase.models.Movie;
import com.example.moviebase.models.MovieDetails;
import com.example.moviebase.models.MovieReview;
import com.example.moviebase.models.MovieTrailer;
import com.example.moviebase.viewmodels.MovieDetailsViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsViewModel movieDetailsViewModel;

    private MoviesAdapter similarMoviesAdapter;
    private ArrayList<Movie> similarMoviesList;

    private MovieReviewsAdapter movieReviewsAdapter;
    private ArrayList<MovieReview> movieReviewsList;

    private MovieTrailersAdapter movieTrailersAdapter;
    private ArrayList<MovieTrailer> movieTrailersList;

    private ActivityMovieInformationBinding activityMovieInformationBinding;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        // Binding Class
        activityMovieInformationBinding = DataBindingUtil.setContentView(this ,R.layout.activity_movie_information);
        activityMovieInformationBinding.setLifecycleOwner(this);

        movie = (Movie) getIntent().getSerializableExtra("SelectedMovie");
        activityMovieInformationBinding.setMovie(movie);

      movieDetailsViewModel.init();
      movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

      // request for more movie details
      movieDetailsViewModel.getMovieDetailsData(movie.getId());
      movieDetailsViewModel.getSimilarMoviesListData(movie.getId() , 1);
      movieDetailsViewModel.getMovieTrailersListData(movie.getId());
      movieDetailsViewModel.getMovieReviewsListData(movie.getId() , 1);


      // Movie Details Section
      movieDetailsViewModel.getMovieDetails().observe(this, new Observer< MovieDetails >() {
          @Override
          public void onChanged(MovieDetails movieDetails) {
              activityMovieInformationBinding.setMovieDetails(movieDetails);
          }
      });
      ////

      // Similar Movies Section
      similarMoviesList = new ArrayList<>();
      similarMoviesAdapter = new MoviesAdapter(similarMoviesList);

      activityMovieInformationBinding.rvSimilarMovies.setLayoutManager(new LinearLayoutManager(this , RecyclerView.HORIZONTAL,false));
      activityMovieInformationBinding.rvSimilarMovies.setHasFixedSize(true);
      activityMovieInformationBinding.rvSimilarMovies.setAdapter(similarMoviesAdapter);

      movieDetailsViewModel.getSimilarMoviesList().observe(this, new Observer< ArrayList< Movie > >() {
          @Override
          public void onChanged(ArrayList<Movie> movies) {
              similarMoviesList.addAll(movies);
              similarMoviesAdapter.notifyDataSetChanged();
          }
      });
      /////

      // Movie Reviews Section
      movieReviewsList = new ArrayList<>();
      movieReviewsAdapter = new MovieReviewsAdapter(movieReviewsList);

      activityMovieInformationBinding.rvMovieReviews.setLayoutManager(new LinearLayoutManager(this));
      activityMovieInformationBinding.rvMovieReviews.setHasFixedSize(true);
      activityMovieInformationBinding.rvMovieReviews.setAdapter(movieReviewsAdapter);

      movieDetailsViewModel.getMovieReviewsList().observe(this, new Observer< ArrayList< MovieReview > >() {
          @Override
          public void onChanged(ArrayList< MovieReview > movieReviews) {
              movieReviewsList.addAll(movieReviews);
              movieReviewsAdapter.notifyDataSetChanged();
          }
      });
      ////


      // Movie Trailers Section
      movieTrailersList = new ArrayList<>();
      movieTrailersAdapter = new MovieTrailersAdapter(movieTrailersList);

      activityMovieInformationBinding.rvMovieTrailers.setLayoutManager(new LinearLayoutManager(this , RecyclerView.HORIZONTAL ,false));
      activityMovieInformationBinding.rvMovieTrailers.setHasFixedSize(true);
      activityMovieInformationBinding.rvMovieTrailers.setAdapter(movieTrailersAdapter);


      movieDetailsViewModel.getMovieTrailersList().observe(this, new Observer< ArrayList< MovieTrailer > >() {
          @Override
          public void onChanged(ArrayList< MovieTrailer > movieTrailers) {
              movieTrailersList.addAll(movieTrailers);
              movieTrailersAdapter.notifyDataSetChanged();
          }
      });
      ////

    }

}
