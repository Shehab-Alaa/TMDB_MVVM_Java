package com.example.moviebase.dagger;


import com.example.moviebase.dagger.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector< ? extends DaggerApplication > applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
