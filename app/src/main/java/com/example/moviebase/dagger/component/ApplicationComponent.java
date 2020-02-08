package com.example.moviebase.dagger.component;


import android.app.Application;

import com.example.moviebase.dagger.MyApplication;
import com.example.moviebase.dagger.modules.AppModule;
import com.example.moviebase.dagger.modules.ApplicationContextModule;
import com.example.moviebase.repositories.DataRepository;
import com.example.moviebase.views.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component (modules = {ApplicationContextModule.class , AppModule.class})
public interface ApplicationComponent {
    //void injectRepository(DataRepository dataRepository);
    void inject(MainActivity mainActivity);
}
