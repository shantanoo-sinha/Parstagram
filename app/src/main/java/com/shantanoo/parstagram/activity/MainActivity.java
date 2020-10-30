package com.shantanoo.parstagram.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.shantanoo.parstagram.LoginActivity;
import com.shantanoo.parstagram.R;
import com.shantanoo.parstagram.fragments.ComposeFragment;
import com.shantanoo.parstagram.fragments.PostsFragment;
import com.shantanoo.parstagram.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.mnuHome:
                    fragment = new PostsFragment();
                    break;
                case R.id.mnuCompose:
                    fragment = new ComposeFragment();
                    break;
                case R.id.mnuProfile:
                default:
                    fragment = new ProfileFragment();
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.mnuHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.mnuLogout) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser == null)
                return true;

            Log.d(TAG, "onOptionsItemSelected: Logging out.");
            ParseUser.logOut();

            Log.d(TAG, "onOptionsItemSelected: Log out done. Sending to Login.");
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.e(TAG, "onOptionsItemSelected: Unknown menu item");
            Toast.makeText(MainActivity.this, "Unknown menu item", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}