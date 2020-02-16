package com.example.moviebase.ui.main.movie_details;

import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.ui.main.movie.MoviesAdapter;
import com.example.moviebase.databinding.ActivityMovieInformationBinding;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.data.model.MovieDetails;
import com.example.moviebase.data.model.MovieReview;
import com.example.moviebase.data.model.MovieTrailer;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

public class MovieDetailsActivity extends DaggerAppCompatActivity {

    private MovieDetailsViewModel movieDetailsViewModel;

    private MoviesAdapter similarMoviesAdapter;
    private ArrayList<Movie> similarMoviesList;

    private MovieReviewsAdapter movieReviewsAdapter;
    private ArrayList<MovieReview> movieReviewsList;

    private MovieTrailersAdapter movieTrailersAdapter;
    private ArrayList<MovieTrailer> movieTrailersList;
    private ActivityMovieInformationBinding activityMovieInformationBinding;
    private Movie movie;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        // Binding Class
        activityMovieInformationBinding = DataBindingUtil.setContentView(this ,R.layout.activity_movie_information);
        activityMovieInformationBinding.setLifecycleOwner(this);

        movie = (Movie) getIntent().getSerializableExtra("SelectedMovie");
        activityMovieInformationBinding.setMovie(movie);

        movieDetailsViewModel = new ViewModelProvider(this , viewModelFactory).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.checkFavoriteMovies(movie.getId()); // to set UI and observe favorite logic

        activityMovieInformationBinding.setEventHandler(movieDetailsViewModel);

        setLayoutAnimation();

        // API Requests Section
        getMovieDetailsApiCall(movie.getId());
        getSimilarMoviesApiCall(movie.getId(),1);
        getMovieTrailersApiCall(movie.getId());
        getMovieReviewsApiCall(movie.getId(),1);
        ///

        // Favorite FAB
        movieDetailsViewModel.getIsFavorite().observe(this, new Observer< Boolean >() {
            @Override
            public void onChanged(Boolean isFavorite) {
                if (isFavorite){
                    activityMovieInformationBinding.fabFavorite.setImageResource(R.drawable.ic_favorite);
                }
                else{
                    activityMovieInformationBinding.fabFavorite.setImageResource(R.drawable.ic_un_favorite);
                }
            }
        });
        //

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
        similarMoviesAdapter = new MoviesAdapter(similarMoviesList , movieDetailsViewModel);

        initRecyclerView(activityMovieInformationBinding.rvSimilarMovies , similarMoviesAdapter , RecyclerView.HORIZONTAL);

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

        initRecyclerView(activityMovieInformationBinding.rvMovieReviews , movieReviewsAdapter , RecyclerView.VERTICAL);

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

        initRecyclerView(activityMovieInformationBinding.rvMovieTrailers,movieTrailersAdapter,RecyclerView.HORIZONTAL);

        movieDetailsViewModel.getMovieTrailersList().observe(this, new Observer< ArrayList< MovieTrailer > >() {
            @Override
            public void onChanged(ArrayList< MovieTrailer > movieTrailers) {
                movieTrailersList.addAll(movieTrailers);
                movieTrailersAdapter.notifyDataSetChanged();
            }
        });
        ////
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setLayoutAnimation(){

        LayoutAnimationController rightAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);
        activityMovieInformationBinding.movieDetailsLayout.setLayoutAnimation(rightAnimationController);

        LayoutAnimationController bottomAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        activityMovieInformationBinding.movieOverviewLayout.setLayoutAnimation(bottomAnimationController);

        // Shard Element Movie Poster
        activityMovieInformationBinding.moviePoster.setTransitionName(movie.getId().toString());
    }

    private void getMovieDetailsApiCall(int movieID){
        movieDetailsViewModel.getMovieDetailsData(movieID);
    }

    private void getSimilarMoviesApiCall(int movieID , int page){
        movieDetailsViewModel.getSimilarMoviesListData(movieID , page);
    }

    private void getMovieTrailersApiCall(int movieID){
        movieDetailsViewModel.getMovieTrailersListData(movieID);
    }

    private void getMovieReviewsApiCall(int movieID , int page){
        movieDetailsViewModel.getMovieReviewsListData(movieID , page);
    }

    private void initRecyclerView(RecyclerView recyclerView , RecyclerView.Adapter adapter , int orientation)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this , orientation,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
