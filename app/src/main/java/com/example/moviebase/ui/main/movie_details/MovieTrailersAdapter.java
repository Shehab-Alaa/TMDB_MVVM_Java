package com.example.moviebase.ui.main.movie_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieTrailerBinding;
import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.ui.base.BaseRecyclerViewAdapter;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.eventhandlers.OnMovieTrailerClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MovieTrailersAdapter  extends BaseRecyclerViewAdapter<MovieTrailer> {

    private OnMovieTrailerClickListener onMovieTrailerClickListener;

    public MovieTrailersAdapter(List< MovieTrailer > items) {
        super(items);
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
        return new MovieTrailersViewHolder(itemMovieTrailerBinding);
    }

    public class MovieTrailersViewHolder extends BaseViewHolder {

        ItemMovieTrailerBinding itemMovieTrailerBinding;

        public MovieTrailersViewHolder(ItemMovieTrailerBinding itemMovieTrailerBinding) {
            super(itemMovieTrailerBinding.getRoot());
            this.itemMovieTrailerBinding = itemMovieTrailerBinding;
            this.itemMovieTrailerBinding.setEventHandler(onMovieTrailerClickListener);
        }

        @Override
        public void onBind(int position) {
            itemMovieTrailerBinding.setMovieTrailer(getItems().get(position));
        }
    }
}