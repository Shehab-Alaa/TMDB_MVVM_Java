package com.example.moviebase.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ActivityMainBinding;
import com.example.moviebase.ui.base.BaseActivity;
import com.example.moviebase.ui.main.favorite.FavoriteMoviesFragment;
import com.example.moviebase.ui.main.movie.MoviesFragment;
import com.example.moviebase.ui.main.movie_details.MovieDetailsViewModel;
import com.example.moviebase.utils.AppConstants;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;


public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

        if (savedInstanceState == null)
        {
            openFragment(AppConstants.NOW_PLAYING);
            getViewDataBinding().navView.setCheckedItem(R.id.nowPlayingMoviesItem);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel initViewModel() {
        return new ViewModelProvider(this , viewModelFactory).get(MainViewModel.class);
    }

    public void initUI(){
        setSupportActionBar(getViewDataBinding().toolbar);
        getViewDataBinding().navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , getViewDataBinding().drawerLayout , getViewDataBinding().toolbar
                , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        getViewDataBinding().drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(getViewDataBinding().drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getViewDataBinding().drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nowPlayingMoviesItem:
                getViewDataBinding().toolbar.setTitle("Now Playing Movies");
                openFragment(AppConstants.NOW_PLAYING);
                break;
            case R.id.popularMoviesItem:
                getViewDataBinding().toolbar.setTitle("Popular Movies");
                openFragment(AppConstants.POPULAR);
                break;
            case R.id.topRatedMoviesItem:
                getViewDataBinding().toolbar.setTitle("Top Rated Movies");
                openFragment(AppConstants.TOP_RATED);
                break;
            case R.id.upcomingMoviesItem:
                getViewDataBinding().toolbar.setTitle("Upcoming Movies");
                openFragment(AppConstants.UPCOMING);
                break;
            case R.id.favoriteMoviesItem:
                getViewDataBinding().toolbar.setTitle("Favorite Movies");
                openFragment(AppConstants.FAVORITE);
                break;
        }

        getViewDataBinding().drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openFragment(String category)
    {
        Fragment fragment;

        if (category.equals("favorite")){
            fragment = new FavoriteMoviesFragment();
        }
        else{
            fragment = new MoviesFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.SELECTED_CATEGORY , category);
            fragment.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container ,
                fragment).commit();
    }

}

