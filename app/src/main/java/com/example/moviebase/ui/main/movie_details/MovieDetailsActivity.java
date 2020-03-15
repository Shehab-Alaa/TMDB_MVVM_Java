package com.example.moviebase.ui.main.movie_details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.data.remote.client.ApiClient;
import com.example.moviebase.ui.base.BaseActivity;
import com.example.moviebase.ui.main.movie.MoviesAdapter;
import com.example.moviebase.databinding.ActivityMovieInformationBinding;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.PicassoCache;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailsActivity extends BaseActivity<ActivityMovieInformationBinding,MovieDetailsViewModel>
        implements MoviesAdapter.MoviesAdapterListener , MovieTrailersAdapter.MovieTrailersAdapterListener{

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

        // TODO :: set Movie Data Binding and view model as a handler (delete handler)
        // TODO :: all view models must hold all data and view observe them (Handlers , Loaders); in base view model;

        movie = (Movie) getIntent().getSerializableExtra(AppConstants.SELECTED_MOVIE);
        getViewDataBinding().setMovie(movie);

        getViewModel().checkFavoriteMovies(movie.getId()); // to set UI and observe favorite logic

        getViewDataBinding().setMovieDetailsViewModel(getViewModel());

        // Shard Element Movie Poster
        getViewDataBinding().moviePoster.setTransitionName(movie.getId().toString());

        getViewDataBinding().collapsingToolbar.setTitleEnabled(true);
        getViewDataBinding().collapsingToolbar.setTitle(movie.getTitle());

        setLayoutAnimation();

        // API Requests Section
        getMovieDetailsApiCall(movie.getId());
        getSimilarMoviesApiCall(movie.getId(),1);
        getMovieTrailersApiCall(movie.getId());
        getMovieReviewsApiCall(movie.getId(),1);
        ///


        // Movie Details Section
        getViewModel().getMovieDetails().observe(this, movieDetails -> getViewDataBinding().setMovieDetails(movieDetails));
        ////

        // Similar Movies Section
        similarMoviesAdapter.setListener(this);
        initRecyclerView(getViewDataBinding().rvSimilarMovies , similarMoviesAdapter , RecyclerView.HORIZONTAL);
        /////

        // Movie Reviews Section
        initRecyclerView(getViewDataBinding().rvMovieReviews , movieReviewsAdapter , RecyclerView.VERTICAL);
        ////

        // Movie Trailers Section
        movieTrailersAdapter.setOnMovieTrailerClickListener(this);
        initRecyclerView(getViewDataBinding().rvMovieTrailers,movieTrailersAdapter,RecyclerView.HORIZONTAL);
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
        getSimilarMoviesApiCall(movie.getId() , 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View itemView,Movie movieItem) {
        Intent intent = new Intent(this , MovieDetailsActivity.class);
        intent.putExtra(AppConstants.SELECTED_MOVIE, movieItem);
        // set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this,
                        itemView.findViewById(R.id.movie_poster),
                        Objects.requireNonNull(ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster))));
        startActivity(intent , options.toBundle());
    }

    @Override
    public void onMovieTrailersRetry() {
        getMovieTrailersApiCall(movie.getId());
    }

    @Override
    public void onMovieTrailerClick(MovieTrailer movieTrailer) {
        openYoutubeApp(movieTrailer.getKey());
    }

    private void openYoutubeApp(String videoId){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_APP_LINK + videoId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_WEB_LINK + videoId));
        try {
            getApplicationContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            getApplicationContext().startActivity(webIntent);
        }
    }


}
