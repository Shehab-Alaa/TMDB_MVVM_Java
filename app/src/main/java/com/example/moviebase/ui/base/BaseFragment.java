package com.example.moviebase.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends ViewDataBinding , V extends ViewModel> extends DaggerFragment {

    protected View mRootView;
    protected Context context;
    protected T mViewDataBinding;
    protected V mViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        // TODO:: Base View Model;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
    }

    public abstract V getViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId() , container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    public abstract int getLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setLifecycleOwner(this);
    }

    public T getViewDataBinding(){
        return mViewDataBinding;
    }
}
