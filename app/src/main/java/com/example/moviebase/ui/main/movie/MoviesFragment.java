package com.example.moviebase.ui.main.movie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.databinding.FragmentMoviesBinding;
import com.example.moviebase.utils.GridSpacingItemDecorationUtils;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.RecyclerViewScrollListenerUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class MoviesFragment extends DaggerFragment   {

    private Context context;
    private MoviesViewModel moviesViewModel;
    private String category;
    private MoviesAdapter moviesAdapter;
    private FragmentMoviesBinding moviesBinding;
    private ArrayList<Movie> moviesList;
    private GridLayoutManager gridLayoutManager;
    private int totalMoviesPages;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        // hold context from an Activity that there life cycles are tied together
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moviesBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_movies, container, false);
        View view = moviesBinding.getRoot();

        category = getArguments().getString("category"); // request API to get all movies in this Category

        // specify view model of this Fragment
        moviesViewModel = new ViewModelProvider(this , viewModelFactory).get(MoviesViewModel.class);

        moviesList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(moviesList , moviesViewModel);

        moviesBinding.setLifecycleOwner(this);
        moviesBinding.progressBar.setVisibility(View.VISIBLE);

        initMoviesRecyclerView(2 ,25);

        getMoviesDataApiCall(1);
        observeMoviesListData();
        observeTotalMoviesPages();
        setupEndlessRecyclerView();

        return view;
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
        moviesViewModel.getTotalMoviesPages().observe(getViewLifecycleOwner(), new Observer< Integer >() {
            @Override
            public void onChanged(Integer pages) {
                totalMoviesPages = pages;
            }
        });
    }

    private void setupEndlessRecyclerView(){
        RecyclerViewScrollListenerUtils rvScrollListenerUtils = new RecyclerViewScrollListenerUtils(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (page <= totalMoviesPages){
                    moviesViewModel.getMoviesListApiCall(category,page);
                }
            }
        };
        moviesBinding.moviesRv.addOnScrollListener(rvScrollListenerUtils);
    }

    private void observeMoviesListData(){
        moviesViewModel.getMoviesList().observe(getViewLifecycleOwner() , new Observer< List< Movie > >() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null){
                    updateMoviesList(movies);
                }else{
                    Log.i("Here" , "No Data Changed");
                }
            }
        });
    }

    private void updateMoviesList(List<Movie> movies){
        moviesBinding.progressBar.setVisibility(View.INVISIBLE);
        moviesList.addAll(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    private void getMoviesDataApiCall(int page){
        moviesViewModel.getMoviesListApiCall(category,page);
    }

}

