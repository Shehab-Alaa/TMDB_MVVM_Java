package com.example.moviebase.dagger.component;


import android.app.Application;

import com.example.moviebase.dagger.BaseApplication;
import com.example.moviebase.dagger.builders.ActivitiesBuilderModule;
import com.example.moviebase.dagger.modules.AppModule;
import com.example.moviebase.dagger.builders.FragmentsBuilderModule;
import com.example.moviebase.dagger.modules.ViewModelFactoryModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component (modules = {
          AndroidSupportInjectionModule.class
        , AppModule.class
        , ViewModelFactoryModule.class
        , ActivitiesBuilderModule.class
        , FragmentsBuilderModule.class})
public interface ApplicationComponent extends AndroidInjector< BaseApplication > {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
