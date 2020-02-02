package com.example.moviebase.dagger;

import android.app.Application;

import com.example.moviebase.dagger.component.ApplicationComponent;
import com.example.moviebase.dagger.component.DaggerApplicationComponent;
import com.example.moviebase.dagger.modules.ApplicationContextModule;

public class MyApplication extends Application {

    static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
