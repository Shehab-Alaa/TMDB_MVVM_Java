package com.example.moviebase.dagger;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MyApplication extends DaggerApplication {

    @Override
    protected AndroidInjector< ? extends DaggerApplication > applicationInjector() {
        return null;
    }
}
