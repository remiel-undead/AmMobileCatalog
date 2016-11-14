package com.example.undead.ammobilecatalog;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.undead.ammobilecatalog.fragment.BlankFragment;
import com.example.undead.ammobilecatalog.fragment.CatalogFragment;
import com.example.undead.ammobilecatalog.fragment.HomeFragment;
import com.example.undead.ammobilecatalog.fragment.TematicSetsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final String STATE_DRAWER = "drawer";

    private int mDrawerItemId = 0;

    private Fragment mCatalogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mDrawerItemId = savedInstanceState.getInt(STATE_DRAWER, 0);
        } else {
            mDrawerItemId = 0;
        }
        if (mDrawerItemId == 0) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, HomeFragment.newInstance()).commit();
        } else {
            onNavigationItemSelected(navigationView.getMenu().findItem(mDrawerItemId));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_DRAWER, mDrawerItemId);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mCatalogFragment != null && mCatalogFragment.isVisible()) {
                ((CatalogFragment) mCatalogFragment).onBackPressed();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        mDrawerItemId = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.nav_catalog) {
            mCatalogFragment = fragmentManager.findFragmentByTag(CatalogFragment.FRAGMENT_TAG_CATALOG);
            if (mCatalogFragment == null) {
                mCatalogFragment = CatalogFragment.newInstance();
            }
            fragmentManager.beginTransaction().replace(R.id.frame_container, mCatalogFragment,
                    CatalogFragment.FRAGMENT_TAG_CATALOG).commit();
        } else if (id == R.id.nav_sets) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, TematicSetsFragment.newInstance()).commit();
        } else if (id == R.id.nav_promos) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, BlankFragment.newInstance()).commit();
        } else if (id == R.id.nav_stores) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, BlankFragment.newInstance()).commit();
        } else if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.frame_container, BlankFragment.newInstance()).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.frame_container, HomeFragment.newInstance()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
