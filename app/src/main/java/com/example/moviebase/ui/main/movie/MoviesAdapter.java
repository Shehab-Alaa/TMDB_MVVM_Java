package com.example.moviebase.ui.main.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieBinding;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;
import com.example.moviebase.data.model.Movie;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<Movie> movies;
    private OnMovieItemClickListener onMovieItemClick;

    public MoviesAdapter(ArrayList<Movie> movies){
        this.movies = movies;
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

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addAll(ArrayList<Movie> movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
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
            itemMovieBinding.setMovie(movies.get(position));
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

