package com.example.moviebase.ui.main.favorite;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.FragmentFavoriteMoviesBinding;
import com.example.moviebase.ui.base.BaseFragment;
import com.example.moviebase.utils.GridSpacingItemDecorationUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

public class FavoriteMoviesFragment extends BaseFragment<FragmentFavoriteMoviesBinding,FavoriteMoviesViewModel> {

    private FavoriteMoviesViewModel favoriteMoviesViewModel;
    private FragmentFavoriteMoviesBinding favoriteMoviesBinding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    FavoriteMoviesAdapter favoriteMoviesAdapter;


    @Override
    public FavoriteMoviesViewModel getViewModel() {
        favoriteMoviesViewModel = new ViewModelProvider(this , viewModelFactory).get(FavoriteMoviesViewModel.class);
        return favoriteMoviesViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteMoviesAdapter.setOnMovieItemClickListener(favoriteMoviesViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteMoviesBinding = getViewDataBinding();
        favoriteMoviesBinding.progressBar.setVisibility(View.VISIBLE);

        initMoviesRecyclerView(2 ,25);
        getFavoriteMovies();
        observeFavoriteMoviesListData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite_movies;
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
