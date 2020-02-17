package com.example.moviebase.ui.main.favorite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.databinding.ItemFavoriteMovieBinding;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClick;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter< FavoriteMoviesAdapter.MoviesViewHolder> {

    private ArrayList< Movie > favoriteMoviesList;
    private OnMovieItemClick onMovieItemClick;

    public FavoriteMoviesAdapter(ArrayList<Movie> movies){
        this.favoriteMoviesList = movies;
    }

    public void setOnMovieItemClickListener(OnMovieItemClick onMovieItemClick) {
        this.onMovieItemClick = onMovieItemClick;
    }

    public void addAll(ArrayList< Movie > favoriteMoviesList){
        this.favoriteMoviesList.addAll(favoriteMoviesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMoviesAdapter.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteMovieBinding itemFavoriteMovieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_favorite_movie,parent ,false
        );
        return new FavoriteMoviesAdapter.MoviesViewHolder(itemFavoriteMovieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMoviesAdapter.MoviesViewHolder holder, final int position) {
        holder.onBindMovie(favoriteMoviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteMoviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder  {

        ItemFavoriteMovieBinding itemFavoriteMovieBinding;

        public MoviesViewHolder(@NonNull ItemFavoriteMovieBinding itemFavoriteMovieBinding) {
            super(itemFavoriteMovieBinding.getRoot());
            this.itemFavoriteMovieBinding = itemFavoriteMovieBinding;
            this.itemFavoriteMovieBinding.setEventHandler(onMovieItemClick);
        }

        public void onBindMovie(Movie movie) {
            // set Data to variable to set each specific Item
            itemFavoriteMovieBinding.setMovie(movie);
        }

    }

}
