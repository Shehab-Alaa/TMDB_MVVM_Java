package com.example.moviebase.ui.base;


import android.view.View;

public interface BaseItemListener<T> {
     void onItemClick(View view, T item);
}
