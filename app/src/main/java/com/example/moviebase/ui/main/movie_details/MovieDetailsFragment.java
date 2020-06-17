package com.example.moviebase.ui.main.movie_details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.databinding.FragmentMovieDetailsBinding;
import com.example.moviebase.ui.base.BaseFragment;
import com.example.moviebase.ui.main.movie.MoviesAdapter;
import com.example.moviebase.utils.AppConstants;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailsFragment extends BaseFragment< FragmentMovieDetailsBinding , MovieDetailsViewModel >
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initToolbar();
        if (getArguments() != null){
            MovieDetailsFragmentArgs args = MovieDetailsFragmentArgs.fromBundle(getArguments());
            movie = args.getSelectedMovie();
            getViewDataBinding().setMovie(movie);
            getViewModel().checkFavoriteMovies(movie.getId()); // to set UI and observe favorite logic

            getViewDataBinding().setMovieDetailsViewModel(getViewModel());

            // Shard Element Movie Poster
            //getViewDataBinding().moviePoster.setTransitionName(movie.getId().toString());

            getViewDataBinding().collapsingToolbar.setTitleEnabled(true);
            getViewDataBinding().collapsingToolbar.setTitle(movie.getTitle());
        }

        return getRootView();
    }

    private void initToolbar() {
        CollapsingToolbarLayout layout = getViewDataBinding().collapsingToolbar;
        Toolbar toolbar = getViewDataBinding().toolbar;
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(layout, toolbar, navController, appBarConfiguration);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLayoutAnimation();

        // API Requests Section
        getMovieDetailsApiCall(movie.getId());
        getSimilarMoviesApiCall(movie.getId(),1);
        getMovieTrailersApiCall(movie.getId());
        getMovieReviewsApiCall(movie.getId(),1);
        ///


        // Movie Details Section
        getViewModel().getMovieDetails().observe(getViewLifecycleOwner(), movieDetails -> getViewDataBinding().setMovieDetails(movieDetails));
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
    public MovieDetailsViewModel initViewModel() {
        return new ViewModelProvider(this , viewModelFactory).get(MovieDetailsViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_details;
    }

    @Override
    public int getBindingVariable() {
        return com.example.moviebase.BR.movieDetailsViewModel;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setLayoutAnimation(){

        LayoutAnimationController rightAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right);
        getViewDataBinding().movieDetailsLayout.setLayoutAnimation(rightAnimationController);

        LayoutAnimationController bottomAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_from_bottom);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , orientation,false));
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
       /*
        // set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(),
                        itemView.findViewById(R.id.movie_poster),
                        Objects.requireNonNull(ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster))));
     */
        MovieDetailsFragmentDirections.ActionMovieDetailsFragmentSelf action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(movieItem);
        getNavController().navigate(action);
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
            assert getContext() != null;
            getContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            assert getContext() != null;
            getContext().startActivity(webIntent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}
