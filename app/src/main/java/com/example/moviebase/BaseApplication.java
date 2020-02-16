package com.example.moviebase;


import com.example.moviebase.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector< ? extends DaggerApplication > applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
