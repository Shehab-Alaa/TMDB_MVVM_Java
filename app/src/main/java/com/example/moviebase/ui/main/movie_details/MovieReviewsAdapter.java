package com.example.moviebase.ui.main.movie_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieReviewBinding;
import com.example.moviebase.data.model.MovieReview;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewVHolder>{

    private ArrayList<MovieReview> movieReviews;

    public MovieReviewsAdapter(ArrayList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    @NonNull
    @Override
    public MovieReviewVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieReviewBinding itemMovieReviewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_movie_review,parent ,false
        );
        return new MovieReviewVHolder(itemMovieReviewBinding);
    }

    @Override
    public void onBindViewHolder(MovieReviewVHolder movieReviewVHolder, int position) {
        movieReviewVHolder.onBind(movieReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public void addAll(ArrayList<MovieReview> movieReviews){
        this.movieReviews.addAll(movieReviews);
        notifyDataSetChanged();
    }

    class MovieReviewVHolder extends RecyclerView.ViewHolder {

        ItemMovieReviewBinding itemMovieReviewBinding;

        public MovieReviewVHolder(ItemMovieReviewBinding itemMovieReviewBinding) {
            super(itemMovieReviewBinding.getRoot());
            this.itemMovieReviewBinding = itemMovieReviewBinding;
        }

        public void onBind(MovieReview movieReview){
            itemMovieReviewBinding.setMovieReview(movieReview);
        }
    }
}
