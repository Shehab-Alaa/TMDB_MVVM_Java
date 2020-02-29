package com.example.moviebase.ui.main.movie_details;

import android.os.Build;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.ui.base.BaseActivity;
import com.example.moviebase.ui.main.movie.MoviesAdapter;
import com.example.moviebase.databinding.ActivityMovieInformationBinding;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.AppConstants;
import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailsActivity extends BaseActivity<ActivityMovieInformationBinding,MovieDetailsViewModel> implements MoviesAdapter.MoviesAdapterListener{

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

        movie = (Movie) getIntent().getSerializableExtra(AppConstants.SELECTED_MOVIE);
        getViewDataBinding().setMovie(movie);

        getViewModel().checkFavoriteMovies(movie.getId()); // to set UI and observe favorite logic

        getViewDataBinding().setEventHandler(getViewModel());

        getViewDataBinding().collapsingToolbar.setTitleEnabled(true);
        getViewDataBinding().collapsingToolbar.setTitle(movie.getTitle());

        setLayoutAnimation();

        // API Requests Section
        getMovieDetailsApiCall(movie.getId());
        getSimilarMoviesApiCall(movie.getId(),1);
        getMovieTrailersApiCall(movie.getId());
        getMovieReviewsApiCall(movie.getId(),1);
        ///

        // Favorite FAB
        getViewModel().getIsFavorite().observe(this, isFavorite -> {
            if (isFavorite){
                getViewDataBinding().fabFavorite.setImageResource(R.drawable.ic_favorite);
            }
            else{
                getViewDataBinding().fabFavorite.setImageResource(R.drawable.ic_un_favorite);
            }
        });
        //

        // Movie Details Section
        getViewModel().getMovieDetails().observe(this, movieDetails -> getViewDataBinding().setMovieDetails(movieDetails));
        ////

        // Similar Movies Section
        similarMoviesAdapter.setListener(this);
        initRecyclerView(getViewDataBinding().rvSimilarMovies , similarMoviesAdapter , RecyclerView.HORIZONTAL);
        getViewModel().getMoviesList().observe(this, movies -> similarMoviesAdapter.addItems(movies));
        /////

        // Movie Reviews Section
        initRecyclerView(getViewDataBinding().rvMovieReviews , movieReviewsAdapter , RecyclerView.VERTICAL);
        getViewModel().getMovieReviewsList().observe(this, movieReviews -> movieReviewsAdapter.addItems(movieReviews));
        ////


        // Movie Trailers Section
        movieTrailersAdapter.setOnMovieTrailerClickListener(getViewModel());
        initRecyclerView(getViewDataBinding().rvMovieTrailers,movieTrailersAdapter,RecyclerView.HORIZONTAL);
        getViewModel().getMovieTrailersList().observe(this, movieTrailers -> movieTrailersAdapter.addItems(movieTrailers));
        ////
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_information;
    }

    @Override
    public MovieDetailsViewModel initViewModel() {
        return new ViewModelProvider(this , viewModelFactory).get(MovieDetailsViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setLayoutAnimation(){

        LayoutAnimationController rightAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);
        getViewDataBinding().movieDetailsLayout.setLayoutAnimation(rightAnimationController);

        LayoutAnimationController bottomAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        getViewDataBinding().movieOverviewLayout.setLayoutAnimation(bottomAnimationController);

        /*// Shard Element Movie Poster
        getViewDataBinding().moviePoster.setTransitionName(movie.getId().toString());*/
    }

    private void getMovieDetailsApiCall(int movieID){
        getViewModel().getMovieDetailsData(movieID);
    }

    private void getSimilarMoviesApiCall(int movieID , int page){
        getViewModel().getSimilarMoviesListData(movieID , page);
    }

    private void getMovieTrailersApiCall(int movieID){
        getViewModel().getMovieTrailersListData(movieID);
    }

    private void getMovieReviewsApiCall(int movieID , int page){
        getViewModel().getMovieReviewsListData(movieID , page);
    }

    private void initRecyclerView(RecyclerView recyclerView , RecyclerView.Adapter adapter , int orientation)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this , orientation,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRetryClick() {

    }

    @Override
    public void onItemClick(Movie item) {

    }
}
