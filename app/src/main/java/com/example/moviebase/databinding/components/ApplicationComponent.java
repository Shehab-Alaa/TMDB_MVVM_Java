package com.example.moviebase.databinding.components;


import com.example.moviebase.clients.ApiService;
import com.example.moviebase.databinding.MyApplication;
import com.example.moviebase.databinding.modules.ApplicationContextModule;
import com.example.moviebase.databinding.modules.RetrofitModule;
import com.example.moviebase.databinding.scopes.ApplicationScope;
import com.example.moviebase.repositories.DataRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@ApplicationScope
@Component (modules = {ApplicationContextModule.class , RetrofitModule.class})
public interface ApplicationComponent {
    void injectRepository(DataRepository dataRepository);
}
