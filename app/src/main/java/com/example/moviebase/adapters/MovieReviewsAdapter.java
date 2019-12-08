package com.example.moviebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ItemMovieBinding;
import com.example.moviebase.databinding.ItemMovieReviewBinding;
import com.example.moviebase.models.MovieReview;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewVHolder>{

private ArrayList<MovieReview> movieReviews;

public MovieReviewsAdapter(ArrayList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
        }

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
