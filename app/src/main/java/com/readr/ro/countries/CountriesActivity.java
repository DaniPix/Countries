package com.readr.ro.countries;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.readr.ro.countries.fragment.CountriesFragment;

/**
 * Launcher activity of the application.
 * Contains a fragment that's displaying the countries.
 */
public class CountriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager.findFragmentByTag(CountriesFragment.class.getName()) == null) {
                Fragment fragment = new CountriesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, CountriesFragment.class.getName())
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
