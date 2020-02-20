package com.example.moviebase.ui.main.movie_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieReviewBinding;
import com.example.moviebase.data.model.MovieReview;
import com.example.moviebase.ui.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieReviewsAdapter extends RecyclerView.Adapter< BaseViewHolder >{

    private ArrayList<MovieReview> movieReviews;

    public MovieReviewsAdapter(ArrayList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieReviewBinding itemMovieReviewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie_review,parent ,false
        );
        return new MovieReviewVHolder(itemMovieReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public void addAll(ArrayList<MovieReview> movieReviews){
        this.movieReviews.addAll(movieReviews);
        notifyDataSetChanged();
    }

    class MovieReviewVHolder extends BaseViewHolder {

        //TODO :: Data Binding set adapter w bet3mlo b3d;
        ItemMovieReviewBinding itemMovieReviewBinding;

        public MovieReviewVHolder(ItemMovieReviewBinding itemMovieReviewBinding) {
            super(itemMovieReviewBinding.getRoot());
            this.itemMovieReviewBinding = itemMovieReviewBinding;
        }

        @Override
        public void onBind(int position) {
            itemMovieReviewBinding.setMovieReview(movieReviews.get(position));
        }
    }
}