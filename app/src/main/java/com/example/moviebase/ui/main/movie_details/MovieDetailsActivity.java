package com.example.moviebase.ui.main.movie_details;

import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.ui.main.movie.MoviesAdapter;
import com.example.moviebase.databinding.ActivityMovieInformationBinding;
import com.example.moviebase.data.model.Movie;

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
    private ActivityMovieInformationBinding activityMovieInformationBinding;
    private Movie movie;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    MoviesAdapter similarMoviesAdapter;

    @Inject
    MovieReviewsAdapter movieReviewsAdapter;

    @Inject
    MovieTrailersAdapter movieTrailersAdapter;

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

        activityMovieInformationBinding.collapsingToolbar.setTitleEnabled(true);
        activityMovieInformationBinding.collapsingToolbar.setTitle(movie.getTitle());

        setLayoutAnimation();

        // API Requests Section
        getMovieDetailsApiCall(movie.getId());
        getSimilarMoviesApiCall(movie.getId(),1);
        getMovieTrailersApiCall(movie.getId());
        getMovieReviewsApiCall(movie.getId(),1);
        ///

        // Favorite FAB
        movieDetailsViewModel.getIsFavorite().observe(this, isFavorite -> {
            if (isFavorite){
                activityMovieInformationBinding.fabFavorite.setImageResource(R.drawable.ic_favorite);
            }
            else{
                activityMovieInformationBinding.fabFavorite.setImageResource(R.drawable.ic_un_favorite);
            }
        });
        //

        // Movie Details Section
        movieDetailsViewModel.getMovieDetails().observe(this, movieDetails -> activityMovieInformationBinding.setMovieDetails(movieDetails));
        ////

        // Similar Movies Section
        similarMoviesAdapter.setOnMovieItemClickListener(movieDetailsViewModel);
        initRecyclerView(activityMovieInformationBinding.rvSimilarMovies , similarMoviesAdapter , RecyclerView.HORIZONTAL);
        movieDetailsViewModel.getSimilarMoviesList().observe(this, movies -> similarMoviesAdapter.addAll(movies));
        /////

        // Movie Reviews Section
        initRecyclerView(activityMovieInformationBinding.rvMovieReviews , movieReviewsAdapter , RecyclerView.VERTICAL);
        movieDetailsViewModel.getMovieReviewsList().observe(this, movieReviews -> movieReviewsAdapter.addAll(movieReviews));
        ////


        // Movie Trailers Section
        movieTrailersAdapter.setOnMovieTrailerClickListener(movieDetailsViewModel);
        initRecyclerView(activityMovieInformationBinding.rvMovieTrailers,movieTrailersAdapter,RecyclerView.HORIZONTAL);
        movieDetailsViewModel.getMovieTrailersList().observe(this, movieTrailers -> movieTrailersAdapter.addAll(movieTrailers));
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
