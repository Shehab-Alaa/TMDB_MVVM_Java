package com.example.moviebase.ui.main.movie;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.BR;
import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.FragmentMoviesBinding;
import com.example.moviebase.ui.base.BaseFragment;
import com.example.moviebase.ui.main.movie_details.MovieDetailsActivity;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.GridSpacingItemDecorationUtils;
import com.example.moviebase.utils.RecyclerViewScrollListenerUtils;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesFragment extends BaseFragment<FragmentMoviesBinding,MoviesViewModel> implements MoviesAdapter.MoviesAdapterListener{

    private String category;
    private GridLayoutManager gridLayoutManager;
    private int totalMoviesPages;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    MoviesAdapter moviesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        category = getArguments().getString(AppConstants.SELECTED_CATEGORY); // request API to get all movies in this Category
        moviesAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkScreenOrientation();
        getMoviesDataApiCall(1);
        observeTotalMoviesPages();
        setupEndlessRecyclerView();
    }

    @Override
    public int getBindingVariable() {
        return BR.moviesViewModel;
    }

    @Override
    public MoviesViewModel initViewModel() {
        return new ViewModelProvider(this , viewModelFactory).get(MoviesViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movies;
    }

    private void checkScreenOrientation(){
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // portrait mode
            initMoviesRecyclerView(2 , 25);
        } else {
            // landscape mode
            initMoviesRecyclerView(4 , 10);
        }
    }

    private void initMoviesRecyclerView(int spanCount , int spacing)
    {
        gridLayoutManager = new GridLayoutManager(getActivity() , spanCount);
        getViewDataBinding().moviesRv.setLayoutManager(gridLayoutManager);
        getViewDataBinding().moviesRv.setHasFixedSize(true);
        // set Animation to all children (items) of this Layout
        int animID = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), animID);
        getViewDataBinding().moviesRv.setLayoutAnimation(animation);
        // equal spaces between grid items
        boolean includeEdge = true;
        getViewDataBinding().moviesRv.addItemDecoration(new GridSpacingItemDecorationUtils(spanCount, spacing, includeEdge));
        getViewDataBinding().moviesRv.setAdapter(moviesAdapter);
    }

    private void observeTotalMoviesPages(){
        totalMoviesPages = 1;
        getViewModel().getTotalMoviesPages().observe(getViewLifecycleOwner(), pages -> totalMoviesPages = pages);
    }

    private void setupEndlessRecyclerView(){
        RecyclerViewScrollListenerUtils rvScrollListenerUtils = new RecyclerViewScrollListenerUtils(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (page <= totalMoviesPages){
                    getMoviesDataApiCall(page);
                }
            }
        };
        getViewDataBinding().moviesRv.addOnScrollListener(rvScrollListenerUtils);
    }

    private void getMoviesDataApiCall(int page){
        getViewModel().getMoviesListApiCall(category,page);
    }

    @Override
    public void onRetryClick() {
        getMoviesDataApiCall(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View itemView,Movie movie) {
        Intent intent = new Intent(getContext() , MovieDetailsActivity.class);
        intent.putExtra(AppConstants.SELECTED_MOVIE, movie);
        // TODO :: you can set this from adapter item binding;
        // set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(),
                        itemView.findViewById(R.id.movie_poster),
                        Objects.requireNonNull(ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster))));
        startActivity(intent , options.toBundle());
    }
}

