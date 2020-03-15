package com.example.moviebase.ui.main.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviebase.databinding.ItemEmptyMovieBinding;
import com.example.moviebase.databinding.ItemMovieBinding;
import com.example.moviebase.ui.base.BaseEmptyItemListener;
import com.example.moviebase.ui.base.BaseItemListener;
import com.example.moviebase.ui.base.BaseRecyclerViewAdapter;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;

import java.util.List;

import androidx.annotation.NonNull;

public class MoviesAdapter extends BaseRecyclerViewAdapter<Movie> {

    private MoviesAdapterListener moviesAdapterListener;

    public MoviesAdapter(List< Movie > items) {
        super(items);
    }

    public void setListener(MoviesAdapterListener moviesAdapterListener) {
        this.moviesAdapterListener = moviesAdapterListener;
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
                return new MoviesViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case AppConstants.VIEW_TYPE_EMPTY:
            default: return new EmptyViewHolder(ItemEmptyMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    public interface MoviesAdapterListener extends BaseItemListener<Movie> , BaseEmptyItemListener{

    }

    public class MoviesViewHolder extends BaseViewHolder implements MovieItemViewModel.MovieItemClickListener , ProgressBarHandler  {

        private ItemMovieBinding itemMovieBinding;

        public MoviesViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
            this.itemMovieBinding.setProgressBarHandler(this);
        }

        @Override
        public void onBind(int position) {
            final Movie movie = getItems().get(position);
            itemMovieBinding.setMovieItemViewModel(new MovieItemViewModel(movie,this));
            itemMovieBinding.executePendingBindings();
        }

        @Override
        public void setProgressBarVisibility(boolean visibility) {
            if (visibility)
                itemMovieBinding.moviePosterLoading.setVisibility(View.VISIBLE);
            else
                itemMovieBinding.moviePosterLoading.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onItemClick(View view,Movie item) {
            if (item != null){
                moviesAdapterListener.onItemClick(view,item);
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements MovieEmptyItemViewModel.MovieEmptyItemListener {

        private ItemEmptyMovieBinding itemEmptyMovieBinding;

        public EmptyViewHolder(@NonNull ItemEmptyMovieBinding itemEmptyMovieBinding) {
            super(itemEmptyMovieBinding.getRoot());
            this.itemEmptyMovieBinding = itemEmptyMovieBinding;
        }

        @Override
        public void onBind(int position) {
            this.itemEmptyMovieBinding.setEmptyItemViewModel(new MovieEmptyItemViewModel(this));
            this.itemEmptyMovieBinding.executePendingBindings();
        }

        @Override
        public void onRetryClick() {
            moviesAdapterListener.onRetryClick();
        }
    }

}

