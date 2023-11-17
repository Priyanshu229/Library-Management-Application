package com.example.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnItemSelectedListener(navListener);
        setupNavigationView();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new booksFragment())
                .commit();



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Handle click events on the hamburger icon
        toggle.setToolbarNavigationClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_share) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareMessage = "Check out this cool app!";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    Intent chooserIntent = Intent.createChooser(shareIntent, "Share via");
                    if (shareIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooserIntent);
                    } else {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else if (item.getItemId() == R.id.menu_rate) {
                    String appPackageName = "com.setmycart.admin";

                    try {
                        // Create an intent to open the app's page on Google Play Store
                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                        rateIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                        // Try opening the Play Store
                        startActivity(rateIntent);
                    } catch (ActivityNotFoundException e) {
                        // If the user doesn't have the Play Store app, open the app page in a browser
                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                        startActivity(rateIntent);
                    }
                    return true;
                } else if (item.getItemId() == R.id.menu_version) {
                    // Implement displaying app version
                    Toast.makeText(MainActivity.this, "1.0.1.3", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private final NavigationBarView.OnItemSelectedListener navListener =
            (NavigationBarView.OnItemSelectedListener) item -> {

                if (item.getItemId() == R.id.nav_books) {
                    // Handle Books tab click
                    // Load Books fragment or perform required action
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new booksFragment())
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.nav_users) {
                    // Handle Users tab click
                    // Load Users fragment or perform required action
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new usersFragment())
                            .commit();
                } else if (item.getItemId() == R.id.nav_library) {
                    // Handle Library tab click
                    // Load Library fragment or perform required action
                    Toast.makeText(MainActivity.this, "library", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}