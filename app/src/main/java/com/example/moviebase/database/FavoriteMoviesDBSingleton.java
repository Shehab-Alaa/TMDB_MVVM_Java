package com.example.moviebase.database;

import android.content.Context;

import androidx.room.Room;

public class FavoriteMoviesDBSingleton {
    private static FavoriteMoviesRoomDB favoriteMoviesRoomDB = null;

    public static FavoriteMoviesRoomDB getFavoriteMoviesRoomDB(Context context)
    {
        if (favoriteMoviesRoomDB == null) {
            // Database access point Not a Presenter
            favoriteMoviesRoomDB = Room.databaseBuilder(context , FavoriteMoviesRoomDB.class , "FavoriteMovies")
                    .allowMainThreadQueries().build();
        }
        return favoriteMoviesRoomDB;
    }
}
