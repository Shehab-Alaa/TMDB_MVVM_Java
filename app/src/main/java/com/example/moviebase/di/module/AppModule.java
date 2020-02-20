package com.example.moviebase.di.module;

import android.app.Application;
import android.content.Context;

import com.example.moviebase.data.remote.client.ApiClient;
import com.example.moviebase.data.remote.ApiHelper;
import com.example.moviebase.data.remote.client.ApiService;
import com.example.moviebase.data.remote.AppApiHelper;
import com.example.moviebase.data.local.db.AppDatabase;
import com.example.moviebase.data.local.db.AppDbHelper;
import com.example.moviebase.data.local.db.DbHelper;
import com.example.moviebase.data.DataRepoHelper;
import com.example.moviebase.data.DataRepository;
import com.example.moviebase.di.DatabaseInfo;
import com.example.moviebase.utils.AppConstants;
import com.example.moviebase.utils.NetworkUtils;

import java.io.IOException;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module (includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    static ApiHelper provideApiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }

    @Provides
    @Singleton
    static ApiService provideApiClient(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static Cache provideCache(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(Interceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();

    }

    @Provides
    @Singleton
    static Interceptor provideInterceptor(final Context context) {
        return chain -> {
            Request request = chain.request();

            if (!NetworkUtils.isNetworkAvailable(context)) {
                int maxStale = 60 * 60 * 24 * 28; // 4-weeks-stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale" + maxStale)
                        .build();
            } else {
                int maxAge = 5; // fresh data
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age = " + maxAge)
                        .build();
            }
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    static DataRepoHelper provideDataRepoHelper(DataRepository dataRepository)
    {
        return dataRepository;
    }

    @Provides
    @Singleton
    static Context provideApplicationContext(Application application){
        return application;
    }


    @Provides
    @Singleton
    static AppDatabase provideFavoriteMoviesRoomDB(Context context , @DatabaseInfo String databaseName){
        return Room.databaseBuilder(context , AppDatabase.class , databaseName)
                .allowMainThreadQueries().build();
    }

    @Provides
    @DatabaseInfo
    static String provideDatabaseName(){
        return AppConstants.DATABASE_NAME;
    }

    @Provides
    @Singleton
    static DbHelper provideAppDbHelper(AppDbHelper appDbHelper){
        return appDbHelper;
    }
}
