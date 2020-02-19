package com.example.moviebase.ui.main.movie_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieTrailerBinding;
import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.eventhandlers.OnMovieTrailerClickListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieTrailersAdapter  extends RecyclerView.Adapter< BaseViewHolder > {

    private ArrayList<MovieTrailer> movieTrailers;
    private OnMovieTrailerClickListener onMovieTrailerClickListener;

    public MovieTrailersAdapter(ArrayList< MovieTrailer > movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public void setOnMovieTrailerClickListener(OnMovieTrailerClickListener onMovieTrailerClickListener){
        this.onMovieTrailerClickListener = onMovieTrailerClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieTrailerBinding itemMovieTrailerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie_trailer,parent ,false
        );
        return new ViewHolder(itemMovieTrailerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public void addAll(ArrayList<MovieTrailer> movieTrailers){
        this.movieTrailers.addAll(movieTrailers);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        ItemMovieTrailerBinding itemMovieTrailerBinding;

        public ViewHolder(ItemMovieTrailerBinding itemMovieTrailerBinding) {
            super(itemMovieTrailerBinding.getRoot());
            this.itemMovieTrailerBinding = itemMovieTrailerBinding;
            this.itemMovieTrailerBinding.setEventHandler(onMovieTrailerClickListener);
        }

        @Override
        public void onBind(int position) {
            itemMovieTrailerBinding.setMovieTrailer(movieTrailers.get(position));
        }
    }
}