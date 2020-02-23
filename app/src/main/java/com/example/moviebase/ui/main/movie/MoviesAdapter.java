package com.example.moviebase.ui.main.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieBinding;
import com.example.moviebase.ui.base.BaseRecyclerViewAdapter;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MoviesAdapter extends BaseRecyclerViewAdapter<Movie> {

    private OnMovieItemClickListener onMovieItemClick;

    public MoviesAdapter(List< Movie > items) {
        super(items);
    }

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClick) {
        this.onMovieItemClick = onMovieItemClick;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie,parent ,false
        );
        return new MoviesViewHolder(itemMovieBinding);
    }

    public class MoviesViewHolder extends BaseViewHolder implements ProgressBarHandler  {

        ItemMovieBinding itemMovieBinding;

        public MoviesViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
            this.itemMovieBinding.setEventHandler(onMovieItemClick);
            this.itemMovieBinding.setProgressBarHandler(this);
        }

        @Override
        public void onBind(int position) {
            itemMovieBinding.setMovie(getItems().get(position));
        }

        @Override
        public void setProgressBarVisibility(boolean visibility) {
            if (visibility)
                itemMovieBinding.moviePosterLoading.setVisibility(View.VISIBLE);
            else
                itemMovieBinding.moviePosterLoading.setVisibility(View.INVISIBLE);
        }
    }

}

