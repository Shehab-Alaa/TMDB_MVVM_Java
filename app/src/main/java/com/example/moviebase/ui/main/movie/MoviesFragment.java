package com.example.moviebase.ui.main.movie;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.databinding.FragmentMoviesBinding;
import com.example.moviebase.ui.base.BaseFragment;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.GridSpacingItemDecorationUtils;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.RecyclerViewScrollListenerUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesFragment extends BaseFragment<FragmentMoviesBinding,MoviesViewModel> {

    private MoviesViewModel moviesViewModel;
    private String category;
    private FragmentMoviesBinding moviesBinding;
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

        moviesAdapter.setOnMovieItemClickListener(moviesViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moviesBinding = getViewDataBinding();

        checkScreenOrientation();
        getMoviesDataApiCall(1);
        observeMoviesListData();
        observeTotalMoviesPages();
        setupEndlessRecyclerView();
    }

    @Override
    public MoviesViewModel getViewModel() {
        moviesViewModel = new ViewModelProvider(this , viewModelFactory).get(MoviesViewModel.class);
        return moviesViewModel;
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
        moviesBinding.moviesRv.setLayoutManager(gridLayoutManager);
        moviesBinding.moviesRv.setHasFixedSize(true);
        // set Animation to all children (items) of this Layout
        int animID = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, animID);
        moviesBinding.moviesRv.setLayoutAnimation(animation);
        // equal spaces between grid items
        boolean includeEdge = true;
        moviesBinding.moviesRv.addItemDecoration(new GridSpacingItemDecorationUtils(spanCount, spacing, includeEdge));
        moviesBinding.moviesRv.setAdapter(moviesAdapter);
    }

    private void observeTotalMoviesPages(){
        totalMoviesPages = 1;
        moviesViewModel.getTotalMoviesPages().observe(getViewLifecycleOwner(), pages -> totalMoviesPages = pages);
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
        moviesBinding.moviesRv.addOnScrollListener(rvScrollListenerUtils);
    }

    private void observeMoviesListData(){
        moviesViewModel.getMoviesList().observe(getViewLifecycleOwner() , movies -> {
            // TODO:: Data binding.
            if (movies != null){
                updateMoviesList(movies);
            }else{
                Log.i("Here" , "No Data Changed");
            }
        });
    }

    private void updateMoviesList(List<Movie> movies){
        //TODO:: observe is Loading ,,
        //TODO:: app constants
        moviesBinding.progressBar.setVisibility(View.INVISIBLE);
        moviesAdapter.addAll((ArrayList< Movie >) movies);
    }

    private void getMoviesDataApiCall(int page){
        moviesBinding.progressBar.setVisibility(View.VISIBLE);
        moviesViewModel.getMoviesListApiCall(category,page);
    }

}

