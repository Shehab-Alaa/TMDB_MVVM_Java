package com.example.moviebase.data.local.db.dao;

import com.example.moviebase.data.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface MoviesDAO {
    @Insert
    void insert(Movie movie);

    @Query("select * from FavoriteMovies")
    LiveData<List<Movie>> loadAll();

    @Query("delete from FavoriteMovies where id = :movieID")
    void delete(long movieID);

    @Query("select COUNT(*) from FavoriteMovies where id = :movieID")
    Single<Integer> isExist(long movieID);

    @Query("select COUNT(*) from FavoriteMovies")
    int countRows();
}
