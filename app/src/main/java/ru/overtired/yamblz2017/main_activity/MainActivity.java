package ru.overtired.yamblz2017.main_activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.overtired.yamblz2017.R;
import ru.overtired.yamblz2017.service.WeatherRequestJob;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.main_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.main_activity_drawer_layout) DrawerLayout drawer;
    @BindView(R.id.main_activity_navigation_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.toggle_open_drawer, R.string.toggle_close_drawer);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_fragment_container, WeatherFragment.newInstance(), WeatherFragment.TAG)
                    .commit();

            navigationView.getMenu().getItem(0).setChecked(true);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstStartKey = getString(R.string.pref_key_first_start);
        boolean isFirstStart = preferences.getBoolean(firstStartKey,true);

        if(isFirstStart){
            preferences.edit().putBoolean(firstStartKey,false).apply();

            boolean autoupdate = preferences.getBoolean(getString(R.string.pref_key_autoupdate),true);

            if(autoupdate){
                long period = Long.parseLong(preferences
                        .getString(getString(R.string.pref_key_autoupdate_interval),
                                getString(R.string.interval_default)));
                WeatherRequestJob.scheduleJob(period);
            }else {
                WeatherRequestJob.unscheduleJob();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.main_activity_nav_content:
                WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager()
                        .findFragmentByTag(WeatherFragment.TAG);
                if(weatherFragment == null){
                    weatherFragment = WeatherFragment.newInstance();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_fragment_container, weatherFragment, WeatherFragment.TAG)
                        .commit();
                break;

            case R.id.main_activity_nav_settings:
                PreferenceFragment preferenceFragment = (PreferenceFragment) getSupportFragmentManager()
                        .findFragmentByTag(PreferenceFragment.TAG);
                if(preferenceFragment == null){
                    preferenceFragment = new PreferenceFragment();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_fragment_container, preferenceFragment,PreferenceFragment.TAG)
                        .commit();
                break;
        }

        if(id==R.id.main_activity_nav_about){
            AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager()
                    .findFragmentByTag(AboutFragment.TAG);
            if(aboutFragment ==null){
                aboutFragment = AboutFragment.newInstance();
            }
            aboutFragment.show(getSupportFragmentManager(),AboutFragment.TAG);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
