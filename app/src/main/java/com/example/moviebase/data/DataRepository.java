package com.example.moviebase.data;


import com.example.moviebase.data.local.db.DatabaseRepository;
import com.example.moviebase.data.remote.ApiRepository;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DataRepository implements DataRepoHelper {

    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;

    @Inject
    public DataRepository(ApiRepository apiRepository , DatabaseRepository databaseRepository){
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
    }

    public ApiRepository getApiRepository() {
        return apiRepository;
    }

    public DatabaseRepository getDatabaseRepository() {
        return databaseRepository;
    }
}


