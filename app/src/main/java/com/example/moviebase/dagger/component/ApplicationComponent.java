package com.example.moviebase.dagger.component;


import com.example.moviebase.dagger.modules.ApplicationContextModule;
import com.example.moviebase.dagger.modules.RetrofitModule;
import com.example.moviebase.dagger.scopes.ApplicationScope;
import com.example.moviebase.repositories.DataRepository;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@ApplicationScope
@Component (modules = {ApplicationContextModule.class , RetrofitModule.class})
public interface ApplicationComponent {
    void injectRepository(DataRepository dataRepository);
}
