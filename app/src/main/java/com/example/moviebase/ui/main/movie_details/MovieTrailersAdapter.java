package com.example.moviebase.ui.main.movie_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.moviebase.data.model.MovieTrailer;
import com.example.moviebase.databinding.ItemEmptyMovieTrailerBinding;
import com.example.moviebase.databinding.ItemMovieTrailerBinding;
import com.example.moviebase.ui.base.BaseRecyclerViewAdapter;
import com.example.moviebase.ui.base.BaseViewHolder;
import com.example.moviebase.utils.AppConstants;

import java.util.List;

import androidx.annotation.NonNull;

public class MovieTrailersAdapter  extends BaseRecyclerViewAdapter< MovieTrailer > {

    private MovieTrailersAdapterListener movieTrailersAdapterListener;

    public MovieTrailersAdapter(List< MovieTrailer > items) {
        super(items);
    }

    public void setOnMovieTrailerClickListener(MovieTrailersAdapterListener movieTrailersAdapterListener){
        this.movieTrailersAdapterListener = movieTrailersAdapterListener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItems() != null && !getItems().isEmpty() ? AppConstants.VIEW_TYPE_NORMAL : AppConstants.VIEW_TYPE_EMPTY;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case AppConstants.VIEW_TYPE_NORMAL:
                return new MovieTrailersViewHolder(ItemMovieTrailerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case AppConstants.VIEW_TYPE_EMPTY:
            default: return new EmptyMovieTrailersViewHolder(ItemEmptyMovieTrailerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    public interface MovieTrailersAdapterListener{
        void onMovieTrailersRetry();
        void onMovieTrailerClick(MovieTrailer movieTrailer);
    }

    public class MovieTrailersViewHolder extends BaseViewHolder implements MovieTrailerItemViewModel.MovieTrailerClickListener  {

        private ItemMovieTrailerBinding itemMovieTrailerBinding;

        public MovieTrailersViewHolder(ItemMovieTrailerBinding itemMovieTrailerBinding) {
            super(itemMovieTrailerBinding.getRoot());
            this.itemMovieTrailerBinding = itemMovieTrailerBinding;
        }

        @Override
        public void onBind(int position) {
            itemMovieTrailerBinding.setMovieTrailerViewModel(new MovieTrailerItemViewModel(getItems().get(position),this));
            itemMovieTrailerBinding.executePendingBindings();
        }

        @Override
        public void onMovieTrailerClick(MovieTrailer movieTrailer) {
            movieTrailersAdapterListener.onMovieTrailerClick(movieTrailer);
        }
    }

    public class EmptyMovieTrailersViewHolder extends BaseViewHolder implements MovieTrailerEmptyItemViewModel.MovieTrailerEmptyItemListener{

        private ItemEmptyMovieTrailerBinding itemEmptyMovieTrailerBinding;

        public EmptyMovieTrailersViewHolder(ItemEmptyMovieTrailerBinding itemEmptyMovieTrailerBinding) {
            super(itemEmptyMovieTrailerBinding.getRoot());
            this.itemEmptyMovieTrailerBinding = itemEmptyMovieTrailerBinding;
        }

        @Override
        public void onBind(int position) {
            itemEmptyMovieTrailerBinding.setEmptyMovieTrailer(new MovieTrailerEmptyItemViewModel(this));
            itemEmptyMovieTrailerBinding.executePendingBindings();

        }

        @Override
        public void onRetryClick() {
            movieTrailersAdapterListener.onMovieTrailersRetry();
        }
    }

}