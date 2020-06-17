package com.example.moviebase.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviebase.R;
import com.example.moviebase.databinding.ActivityMainBinding;
import com.example.moviebase.ui.base.BaseActivity;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel>// implements NavigationView.OnNavigationItemSelectedListener
{

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel initViewModel() {
        return new ViewModelProvider(this , viewModelFactory).get(MainViewModel.class);
    }

    private void initUI(){
        setSupportActionBar(findViewById(R.id.toolbarX));
        DrawerLayout drawer = getViewDataBinding().drawerLayout;
        NavigationView navigationView = getViewDataBinding().navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nowPlayingMoviesItem , R.id.popularMoviesItem , R.id.topRatedMoviesItem ,
                R.id.upcomingMoviesItem , R.id.favoriteMoviesItem)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}

