package com.example.moviebase.ui.main.movie_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieTrailerBinding;
import com.example.moviebase.data.model.MovieTrailer;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieTrailersAdapter  extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {

    private ArrayList<MovieTrailer> movieTrailers;

    public MovieTrailersAdapter(ArrayList< MovieTrailer > movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieTrailerBinding itemMovieTrailerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie_trailer,parent ,false
        );
        return new ViewHolder(itemMovieTrailerBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(movieTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemMovieTrailerBinding itemMovieTrailerBinding;

        public ViewHolder(ItemMovieTrailerBinding itemMovieTrailerBinding) {
            super(itemMovieTrailerBinding.getRoot());
            this.itemMovieTrailerBinding = itemMovieTrailerBinding;
        }

        public void onBind(MovieTrailer movieTrailer){
            itemMovieTrailerBinding.setMovieTrailer(movieTrailer);
        }

    }
}