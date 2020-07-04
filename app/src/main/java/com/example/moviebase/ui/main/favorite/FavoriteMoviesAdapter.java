package com.example.moviebase.ui.main.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.ItemEmptyFavoriteMovieBinding;
import com.example.moviebase.databinding.ItemFavoriteMovieBinding;
import com.example.moviebase.ui.base.BaseItemListener;
import com.example.moviebase.ui.base.BaseRecyclerViewAdapter;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;

import java.util.List;

import androidx.annotation.NonNull;

public class FavoriteMoviesAdapter extends BaseRecyclerViewAdapter< Movie > {

    private FavoritesAdapterListener favoritesAdapterListener;

    public FavoriteMoviesAdapter(List< Movie > items) {
        super(items);
    }

    public void setListener(FavoritesAdapterListener favoritesAdapterListener) {
        this.favoritesAdapterListener = favoritesAdapterListener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems() != null && !getItems().isEmpty() ? AppConstants.VIEW_TYPE_NORMAL : AppConstants.VIEW_TYPE_EMPTY;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case AppConstants.VIEW_TYPE_NORMAL:
                return new FavoriteMoviesAdapter.MoviesViewHolder(ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case AppConstants.VIEW_TYPE_EMPTY:
            default: return new FavoriteMoviesAdapter.EmptyViewHolder(ItemEmptyFavoriteMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    public interface FavoritesAdapterListener extends BaseItemListener< Movie >{

    }

    @Override
    public void addItems(List< Movie > items) {
        this.clearItems();
        super.addItems(items);
    }

    public class MoviesViewHolder extends BaseViewHolder implements FavoriteItemViewModel.FavoriteMovieItemClickListener, ProgressBarHandler {

        private ItemFavoriteMovieBinding itemFavoriteMovieBinding;

        public MoviesViewHolder(@NonNull ItemFavoriteMovieBinding itemFavoriteMovieBinding) {
            super(itemFavoriteMovieBinding.getRoot());
            this.itemFavoriteMovieBinding = itemFavoriteMovieBinding;
            this.itemFavoriteMovieBinding.setProgressBarHandler(this);
        }

        @Override
        public void onBind(int position) {
            final Movie movie = getItems().get(position);
            this.itemFavoriteMovieBinding.setViewModel(new FavoriteItemViewModel(movie,this));
            this.itemFavoriteMovieBinding.executePendingBindings();
        }

        @Override
        public void setProgressBarVisibility(boolean visibility) {
            if (visibility)
                itemFavoriteMovieBinding.moviePosterLoading.setVisibility(View.VISIBLE);
            else
                itemFavoriteMovieBinding.moviePosterLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onItemClick(View view, Movie item) {
            if (item != null){
                favoritesAdapterListener.onItemClick(view,item);
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder{

        private ItemEmptyFavoriteMovieBinding itemEmptyFavoriteMovieBinding;

        public EmptyViewHolder(@NonNull ItemEmptyFavoriteMovieBinding itemEmptyFavoriteMovieBinding) {
            super(itemEmptyFavoriteMovieBinding.getRoot());
            this.itemEmptyFavoriteMovieBinding = itemEmptyFavoriteMovieBinding;
        }

        @Override
        public void onBind(int position) {
            this.itemEmptyFavoriteMovieBinding.setFavoriteEmptyItemViewModel(new FavoriteEmptyItemViewModel());
            this.itemEmptyFavoriteMovieBinding.executePendingBindings();
        }

    }

}
