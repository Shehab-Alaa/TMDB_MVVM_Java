package com.example.moviebase.ui.main.movie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieBinding;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClick;
import com.example.moviebase.data.model.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private ArrayList<Movie> movies;
    private OnMovieItemClick onMovieItemClick;

    public MoviesAdapter(ArrayList<Movie> movies , OnMovieItemClick onMovieItemClick){
        this.movies = movies;
        this.onMovieItemClick = onMovieItemClick;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie,parent ,false
        );
        return new MoviesViewHolder(itemMovieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {
        holder.onBindMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder  {

        ItemMovieBinding itemMovieBinding;

        public MoviesViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
            this.itemMovieBinding.setEventHandler(onMovieItemClick);
        }

        public void onBindMovie(Movie movie) {
            // set Data to variable to set each specific Item
            itemMovieBinding.setMovie(movie);
        }

    }

}

