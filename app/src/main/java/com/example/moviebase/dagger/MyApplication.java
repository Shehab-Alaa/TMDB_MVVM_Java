package com.example.moviebase.dagger;

import android.app.Application;

import com.example.moviebase.dagger.component.ApplicationComponent;
import com.example.moviebase.dagger.component.DaggerApplicationComponent;


public class MyApplication extends Application {

    static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();

    }


    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
