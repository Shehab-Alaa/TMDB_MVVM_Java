package com.example.moviebase.ui.main.favorite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.FragmentFavoriteMoviesBinding;
import com.example.moviebase.utils.GridSpacingItemDecorationUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import dagger.android.support.DaggerFragment;

public class FavoriteMoviesFragment extends DaggerFragment {

    private Context context;
    private FavoriteMoviesViewModel favoriteMoviesViewModel;
    private FragmentFavoriteMoviesBinding favoriteMoviesBinding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    FavoriteMoviesAdapter favoriteMoviesAdapter;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favoriteMoviesBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_favorite_movies, container, false);
        View view = favoriteMoviesBinding.getRoot();


        // specify view model of this Fragment
        favoriteMoviesViewModel = new ViewModelProvider(this , viewModelFactory).get(FavoriteMoviesViewModel.class);

        favoriteMoviesAdapter.setOnMovieItemClickListener(favoriteMoviesViewModel);

        favoriteMoviesBinding.setLifecycleOwner(this);
        favoriteMoviesBinding.progressBar.setVisibility(View.VISIBLE);

        initMoviesRecyclerView(2 ,25);

        getFavoriteMovies();
        observeFavoriteMoviesListData();

        return view;
    }

    private void initMoviesRecyclerView(int spanCount , int spacing)
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity() , spanCount);
        favoriteMoviesBinding.favoriteMoviesRv.setLayoutManager(gridLayoutManager);
        favoriteMoviesBinding.favoriteMoviesRv.setHasFixedSize(true);
        // set Animation to all children (items) of this Layout
        int animID = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, animID);
        favoriteMoviesBinding.favoriteMoviesRv.setLayoutAnimation(animation);
        // equal spaces between grid items
        boolean includeEdge = true;
        favoriteMoviesBinding.favoriteMoviesRv.addItemDecoration(new GridSpacingItemDecorationUtils(spanCount, spacing, includeEdge));
        favoriteMoviesBinding.favoriteMoviesRv.setAdapter(favoriteMoviesAdapter);
    }

    private void getFavoriteMovies(){
        favoriteMoviesViewModel.getFavoriteMoviesList();
    }

    private void observeFavoriteMoviesListData(){
        favoriteMoviesViewModel.getFavoriteMoviesList().observe(getViewLifecycleOwner(), new Observer< List< Movie > >() {
            @Override
            public void onChanged(List< Movie > movies) {
                updateMoviesList(movies);
            }
        });
    }

    private void updateMoviesList(List<Movie> movies){
        favoriteMoviesBinding.progressBar.setVisibility(View.INVISIBLE);
        favoriteMoviesAdapter.addAll((ArrayList< Movie >) movies);
    }
}
