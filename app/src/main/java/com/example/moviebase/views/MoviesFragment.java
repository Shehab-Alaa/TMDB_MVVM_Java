package com.example.moviebase.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.adapters.MoviesAdapter;
import com.example.moviebase.databinding.FragmentMoviesBinding;
import com.example.moviebase.helpers.GridSpacingItemDecoration;
import com.example.moviebase.models.Movie;
import com.example.moviebase.viewmodels.MoviesViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class MoviesFragment extends Fragment {

    private Context context;
    private MoviesViewModel moviesViewModel;
    private String category;
    private MoviesAdapter moviesAdapter;
    private FragmentMoviesBinding moviesBinding;
    private ArrayList<Movie> moviesList;

    @Override
    public void onAttach(Context context) {
        // hold context from an Activity that there lifecycles are tied together
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moviesBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_movies, container, false);
        View view = moviesBinding.getRoot();

        category = getArguments().getString("category");
        // request API to get all movies in this Category

        // specify view model of this Fragment
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.init();

        moviesList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(moviesList , moviesViewModel);

        moviesBinding.setLifecycleOwner(this);
        moviesBinding.progressBar.setVisibility(View.VISIBLE);

        initMoviesRecyclerView(2 ,25);

        getMoviesData(1);

        return view;
    }


    private void initMoviesRecyclerView(int spanCount , int spacing)
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity() , spanCount);
        moviesBinding.moviesRv.setLayoutManager(gridLayoutManager);
        moviesBinding.moviesRv.setHasFixedSize(true);
        // set Animation to all children (items) of this Layout
        int animID = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, animID);
        moviesBinding.moviesRv.setLayoutAnimation(animation);
        // equal spaces between grid items
        boolean includeEdge = true;
        moviesBinding.moviesRv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        moviesBinding.moviesRv.setAdapter(moviesAdapter);
    }

    private void getMoviesData(int page){
        moviesViewModel.getMoviesData(category,page);
        moviesViewModel.getMoviesList().observe(this , new Observer< ArrayList< Movie > >() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null){
                  moviesBinding.progressBar.setVisibility(View.INVISIBLE);
                  moviesList.addAll(movies);
                  moviesAdapter.notifyDataSetChanged();
                }else{
                    Log.i("Here" , "Iam Not Changed");
                }
            }
        });
    }

}

