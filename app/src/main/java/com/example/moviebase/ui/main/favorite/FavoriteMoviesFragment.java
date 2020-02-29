package com.example.moviebase.ui.main.favorite;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.moviebase.BR;
import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.FragmentFavoriteMoviesBinding;
import com.example.moviebase.ui.base.BaseFragment;
import com.example.moviebase.ui.main.movie_details.MovieDetailsActivity;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.GridSpacingItemDecorationUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

public class FavoriteMoviesFragment extends BaseFragment<FragmentFavoriteMoviesBinding,FavoriteMoviesViewModel>
        implements FavoriteMoviesAdapter.FavoritesAdapterListener
{

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    FavoriteMoviesAdapter favoriteMoviesAdapter;

    @Override
    public FavoriteMoviesViewModel initViewModel() {
        return new ViewModelProvider(this , viewModelFactory).get(FavoriteMoviesViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteMoviesAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkScreenOrientation();
    }

    @Override
    public int getBindingVariable() {
        return BR.favoriteMoviesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite_movies;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity() , spanCount);
        getViewDataBinding().favoriteMoviesRv.setLayoutManager(gridLayoutManager);
        getViewDataBinding().favoriteMoviesRv.setHasFixedSize(true);
        // set Animation to all children (items) of this Layout
        int animID = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), animID);
        getViewDataBinding().favoriteMoviesRv.setLayoutAnimation(animation);
        // equal spaces between grid items
        boolean includeEdge = true;
        getViewDataBinding().favoriteMoviesRv.addItemDecoration(new GridSpacingItemDecorationUtils(spanCount, spacing, includeEdge));
        getViewDataBinding().favoriteMoviesRv.setAdapter(favoriteMoviesAdapter);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getContext() , MovieDetailsActivity.class);
        intent.putExtra(AppConstants.SELECTED_MOVIE, movie);
        startActivity(intent);
        /*// set dynamic transition name by MovieID
        itemView.findViewById(R.id.movie_poster).setTransitionName(movie.getId().toString());
        // need to share MoviePoster between this Activity And MovieInformation
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((Activity) itemView.getContext(),
                        itemView.findViewById(R.id.movie_poster),
                        Objects.requireNonNull(ViewCompat.getTransitionName(itemView.findViewById(R.id.movie_poster))));
        itemView.getContext().startActivity(intent , options.toBundle());*/
    }
}
