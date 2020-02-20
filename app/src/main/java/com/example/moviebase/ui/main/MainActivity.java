package com.example.moviebase.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.moviebase.R;
import com.example.moviebase.ui.main.favorite.FavoriteMoviesFragment;
import com.example.moviebase.ui.main.movie.MoviesFragment;
import com.example.moviebase.utils.AppConstants;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends DaggerAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        if (savedInstanceState == null)
        {
            openFragment(AppConstants.NOW_PLAYING);
            navigationView.setCheckedItem(R.id.nowPlayingMoviesItem);
        }
    }

    public void initUI(){
        // TODO :: findViewByID;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar
                , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nowPlayingMoviesItem:
                toolbar.setTitle("Now Playing Movies");
                openFragment(AppConstants.NOW_PLAYING);
                break;
            case R.id.popularMoviesItem:
                toolbar.setTitle("Popular Movies");
                openFragment(AppConstants.POPULAR);
                break;
            case R.id.topRatedMoviesItem:
                toolbar.setTitle("Top Rated Movies");
                openFragment(AppConstants.TOP_RATED);
                break;
            case R.id.upcomingMoviesItem:
                toolbar.setTitle("Upcoming Movies");
                openFragment(AppConstants.UPCOMING);
                break;
            case R.id.favoriteMoviesItem:
                toolbar.setTitle("Favorite Movies");
                openFragment(AppConstants.FAVORITE);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
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

