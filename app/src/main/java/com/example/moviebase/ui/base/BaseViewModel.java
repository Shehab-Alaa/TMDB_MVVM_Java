package com.example.moviebase.ui.base;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.utils.eventhandlers.OnMovieItemClickListener;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<Boolean> isLoading;

    public BaseViewModel(DataRepository dataRepository){
        this.dataRepository = dataRepository;
        isLoading = new MutableLiveData<>(true);
    }

    public MutableLiveData< Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public DataRepository getDataRepository() {
        return dataRepository;
    }

}
