package com.example.moviebase.dagger.modules;


import android.content.Context;

import com.example.moviebase.dagger.qualifiers.ApplicationContextQualifier;
import com.example.moviebase.dagger.scopes.ApplicationScope;

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
