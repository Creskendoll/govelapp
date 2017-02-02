package com.govelapp.govelapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final Pattern queryPattern = Pattern.compile("[a-zA-Z \t/&]+");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu, menu);
        menu.findItem(R.id.search).setVisible(false);
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.search));
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        final MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchMenuItem.expandActionView();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(isValid(query)){
            doSearch(query);
        }else{
            Toast.makeText(this, "Invalid search parameters.", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void doSearch(String query) {
        Intent queryIntent = new Intent(SearchActivity.this, MapsActivity.class);
        queryIntent.putExtra("query", query);
        startActivity(queryIntent);
    }

    //returns true if its a valid query
    private boolean isValid(String s) {
        Matcher mMatch = queryPattern.matcher(s);
        return mMatch.matches();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
