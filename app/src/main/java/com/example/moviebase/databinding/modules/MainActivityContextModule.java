package com.example.moviebase.databinding.modules;

import android.content.Context;

import com.example.moviebase.databinding.qualifiers.ActivityContext;
import com.example.moviebase.databinding.scopes.ActivityScope;
import com.example.moviebase.views.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityContextModule {

    private MainActivity mainActivity;
    public Context context;

    public MainActivityContextModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        context = mainActivity;
    }

    @Provides
    @ActivityScope
    public MainActivity providesMainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideActivityContext() {
        return context;
    }

}
