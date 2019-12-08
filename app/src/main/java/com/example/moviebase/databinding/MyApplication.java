package com.example.moviebase.databinding;

import android.app.Activity;
import android.app.Application;
import android.provider.ContactsContract;

import com.example.moviebase.databinding.components.ApplicationComponent;
import com.example.moviebase.databinding.components.DaggerApplicationComponent;
import com.example.moviebase.databinding.modules.ApplicationContextModule;
import com.example.moviebase.databinding.modules.RetrofitModule;
import com.example.moviebase.repositories.DataRepository;

import javax.inject.Inject;

import retrofit2.internal.EverythingIsNonNull;


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
