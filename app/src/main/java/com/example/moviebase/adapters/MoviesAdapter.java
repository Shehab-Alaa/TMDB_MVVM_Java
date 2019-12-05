package com.example.moviebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieBinding;
import com.example.moviebase.models.Movie;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<Movie> movies;

    public MoviesAdapter(ArrayList<Movie> movies){
        this.movies = movies;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie,parent ,false
        );
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(itemMovieBinding);
        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesViewHolder holder, final int position) {
        holder.onBindMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder  {

        ItemMovieBinding itemMovieBinding;

        public MoviesViewHolder(ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
        }

        public void onBindMovie(Movie movie) {
            itemMovieBinding.moviePosterLoading.setVisibility(View.VISIBLE);
            // set Data to variable to set each specific Item
            itemMovieBinding.setMovie(movie);
            itemMovieBinding.moviePosterLoading.setVisibility(View.INVISIBLE);
        }

    }

}

