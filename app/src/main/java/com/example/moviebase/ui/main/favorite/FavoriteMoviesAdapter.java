package com.example.moviebase.ui.main.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.ItemFavoriteMovieBinding;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<BaseViewHolder > {

    private ArrayList< Movie > favoriteMoviesList;
    private OnMovieItemClickListener onMovieItemClick;

    public FavoriteMoviesAdapter(ArrayList<Movie> movies){
        this.favoriteMoviesList = movies;
    }

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClick) {
        this.onMovieItemClick = onMovieItemClick;
    }

    public void addAll(ArrayList< Movie > favoriteMoviesList){
        this.favoriteMoviesList.addAll(favoriteMoviesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteMovieBinding itemFavoriteMovieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_favorite_movie,parent ,false
        );
        return new MoviesViewHolder(itemFavoriteMovieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return favoriteMoviesList.size();
    }

    public class MoviesViewHolder extends BaseViewHolder implements ProgressBarHandler {

        ItemFavoriteMovieBinding itemFavoriteMovieBinding;

        public MoviesViewHolder(@NonNull ItemFavoriteMovieBinding itemFavoriteMovieBinding) {
            super(itemFavoriteMovieBinding.getRoot());
            this.itemFavoriteMovieBinding = itemFavoriteMovieBinding;
            this.itemFavoriteMovieBinding.setEventHandler(onMovieItemClick);
            this.itemFavoriteMovieBinding.setProgressBarHandler(this);
        }

        @Override
        public void onBind(int position) {
            itemFavoriteMovieBinding.setMovie(favoriteMoviesList.get(position));
        }

        @Override
        public void setProgressBarVisibility(boolean visibility) {
            if (visibility)
                itemFavoriteMovieBinding.moviePosterLoading.setVisibility(View.VISIBLE);
            else
                itemFavoriteMovieBinding.moviePosterLoading.setVisibility(View.INVISIBLE);
        }
    }

}
