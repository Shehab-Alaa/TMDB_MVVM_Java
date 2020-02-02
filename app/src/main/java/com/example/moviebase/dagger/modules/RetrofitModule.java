package com.example.moviebase.dagger.modules;



import android.content.Context;

import com.example.moviebase.clients.ApiClient;
import com.example.moviebase.clients.ApiService;
import com.example.moviebase.dagger.qualifiers.ApplicationContextQualifier;
import com.example.moviebase.dagger.scopes.ApplicationScope;
import com.example.moviebase.helpers.NetworkUtils;


import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module (includes = {ApplicationContextModule.class})
public class RetrofitModule {

    @Provides
    @ApplicationScope
    static ApiService getApiClient(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    static Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    static Cache getCache(@ApplicationContextQualifier Context context) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @ApplicationScope
    static OkHttpClient getOkHttpClient(Interceptor interceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();

    }

    @Provides
    @ApplicationScope
    static Interceptor getInterceptor(@ApplicationContextQualifier final Context context) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
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
            }
        };
        return interceptor;
    }


}

