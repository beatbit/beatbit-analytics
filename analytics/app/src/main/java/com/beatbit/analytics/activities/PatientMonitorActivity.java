package com.beatbit.analytics.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beatbit.analytics.fragments.AverageRatesFragment;
import com.beatbit.analytics.fragments.LiveDataFragment;
import com.beatbit.analytics.R;


public class PatientMonitorActivity extends ActionBarActivity {
    private CharSequence title;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_monitor);

        title = getTitle();

        ListView nav = (ListView) findViewById(R.id.lv_nav);

        final String[] items = getResources().getStringArray(R.array.nav_items);

        nav.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

        setUpDrawerLayout();

        nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items[position].equals(getString(R.string.live))) {
                    setContent(new LiveDataFragment());
                }
                else if(items[position].equals(getString(R.string.past))) {
                    setContent(new AverageRatesFragment());
                }

                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        setContent(new LiveDataFragment());
    }

    private void setUpDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(Color.BLACK);
        drawerLayout.setScrimColor(Color.BLACK);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toggle.syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toggle.syncState();
            }
        };

        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(true);

        drawerLayout.setDrawerListener(toggle);
        drawerLayout.setScrimColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void onNavigationDrawerItemSelected(int position) {

    }

    private void setContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

}
