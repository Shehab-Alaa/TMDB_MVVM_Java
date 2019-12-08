package com.example.moviebase.databinding.modules;


import android.content.Context;

import com.example.moviebase.databinding.qualifiers.ApplicationContextQualifier;
import com.example.moviebase.databinding.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationContextModule {

    private Context context;

    public ApplicationContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    @ApplicationContextQualifier
    public Context provideApplicationContext(){
        return context;
    }
}
