package com.example.moviebase.ui.main;

import com.example.moviebase.data.DataRepository;
import com.example.moviebase.ui.base.BaseViewModel;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {

    @Inject
    public MainViewModel(DataRepository dataRepository) {
        super(dataRepository);
    }
}
