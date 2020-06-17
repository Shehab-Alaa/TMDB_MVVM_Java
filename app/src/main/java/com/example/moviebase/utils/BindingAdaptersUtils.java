package com.example.moviebase.utils;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviebase.R;
import com.example.moviebase.data.model.Category;
import com.example.moviebase.data.remote.client.ApiClient;
import com.example.moviebase.data.remote.client.YoutubeClient;
import com.example.moviebase.ui.base.BaseRecyclerViewAdapter;
import com.example.moviebase.utils.eventhandlers.ProgressBarHandler;
import com.squareup.picasso.Callback;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindingAdaptersUtils {

    @BindingAdapter({"android:backPosterUrl" })
    public static void loadBackPosterImage(final ImageView backPoster, String backPosterUrl){

        PicassoCache
                .getPicassoInstance(backPoster.getContext())
                .load(ApiClient.BACKDROP_BASE_URL + backPosterUrl)
                .into(backPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(Exception e) {
                        backPoster.setImageResource(R.drawable.movie_poster);
                    }
                });
    }

    @BindingAdapter({"android:moviePosterUrl" , "android:progressBarHandler"})
    public static void loadMoviePosterImage(final ImageView moviePoster, String moviePosterUrl , ProgressBarHandler progressBarHandler){
        if (progressBarHandler != null)
            progressBarHandler.setProgressBarVisibility(true);
        PicassoCache
                .getPicassoInstance(moviePoster.getContext())
                .load(ApiClient.POSTER_BASE_URL + moviePosterUrl)
                .into(moviePoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBarHandler != null)
                            progressBarHandler.setProgressBarVisibility(false);
                    }

                    @Override
                    public void onError(Exception e) {
                        moviePoster.setImageResource(R.drawable.movie_poster);
                        if (progressBarHandler != null)
                            progressBarHandler.setProgressBarVisibility(false);
                    }
                });
    }

    @BindingAdapter({"android:ratingBar"})
    public static void setRatingBarView(RatingBar movieRate , double movieVoteAverage){
        float rating = (float) (( movieVoteAverage * 5 ) / 9);
        movieRate.setNumStars(5);
        movieRate.setStepSize(0.1f);
        movieRate.setRating(rating);
        movieRate.setIsIndicator(true);
    }

    @BindingAdapter({"android:trailerImage"})
    public static void setMovieTrailerImage(ImageView movieTrailerThumbnail, String trailerKey){

        PicassoCache
                .getPicassoInstance(movieTrailerThumbnail.getContext())
                .load(YoutubeClient.YOUTUBE_VIDEO_THUMBNAIL + trailerKey + "/0.jpg")
                .into(movieTrailerThumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }


    @BindingAdapter({"android:categoriesText"})
    public static void setCategoriesTextViewData(TextView movieCategories , List< Category > categories){
        String categoriesHolder = "";
        if (categories != null) {
            for (Category category : categories)
                categoriesHolder += category.getName() + ". ";
        }
        movieCategories.setText(categoriesHolder);
    }

    @BindingAdapter({"android:statusImage"})
    public static void setMovieStatusImageView(ImageView movieStatusImage, String movieStatus){
      if (movieStatus != null) {
          if (movieStatus.equals("Released")) {
              movieStatusImage.setImageDrawable(movieStatusImage.getResources().getDrawable(R.drawable.ic_released));
          } else {
              movieStatusImage.setImageDrawable(movieStatusImage.getResources().getDrawable(R.drawable.ic_un_released));
          }
      }
    }

    @BindingAdapter({"android:statusText"})
    public static void setMovieStatusTextView(TextView movieStatusText,  String movieStatus){
        if (movieStatus != null){
        if (movieStatus.equals("Released")){
            movieStatusText.setText(movieStatus);
        }else{
            movieStatusText.setText(movieStatus);
        }

        }
    }

    @BindingAdapter({"android:isAdultMovie"})
    public static void setMovieAdultText(TextView adultMovieText , boolean isAdult){
        if (isAdult)
            adultMovieText.setText("Yes");
        else
            adultMovieText.setText("No");
    }


    @SuppressWarnings("unchecked")
    @BindingAdapter({"adapter"})
    public static <T> void setRecyclerViewData(RecyclerView recyclerView, List<T> items) {
        BaseRecyclerViewAdapter<T> adapter = (BaseRecyclerViewAdapter< T >) recyclerView.getAdapter();
        if (adapter != null) {
           adapter.addItems(items);
        }
    }

}
