package com.example.moviebase.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel > extends DaggerAppCompatActivity {

    private T mViewDataBinding;
    private V mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = initViewModel();
        performDataBinding();
    }

    private void performDataBinding(){
        // Binding Class
        mViewDataBinding = DataBindingUtil.setContentView(this , getLayoutId());
        mViewDataBinding.setLifecycleOwner(this);
    }

    public abstract int getLayoutId();

    public abstract V initViewModel();

    public V getViewModel(){
        return mViewModel;
    }

    public T getViewDataBinding(){
        return mViewDataBinding;
    }

}
