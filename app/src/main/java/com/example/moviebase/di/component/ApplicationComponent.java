package com.example.moviebase.di.component;


import android.app.Application;

import com.example.moviebase.BaseApplication;
import com.example.moviebase.di.builder.ActivityBuilder;
import com.example.moviebase.di.module.AppModule;
import com.example.moviebase.ui.main.MainActivityModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component (modules = {
          AndroidSupportInjectionModule.class
        , AppModule.class
        , MainActivityModule.class
        , ActivityBuilder.class})
public interface ApplicationComponent extends AndroidInjector< BaseApplication > {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
