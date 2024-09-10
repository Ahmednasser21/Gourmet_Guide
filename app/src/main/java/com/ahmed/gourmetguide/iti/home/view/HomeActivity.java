package com.ahmed.gourmetguide.iti.home.view;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        boolean isGuest = sharedPreferences.getBoolean(getString(R.string.preferences_is_guest), false);
        navController = Navigation.findNavController(this, R.id.homeFragmentContainerView);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {

                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.search) {

                navController.navigate(R.id.searchFragment);

            } else if (itemId == R.id.favourit) {

                if (!isGuest) {
                    navController.navigate(R.id.favouriteFragment);
                } else {
                    showSignInDialog();
                }
            } else if (itemId == R.id.plans) {

                if (!isGuest) {
                    navController.navigate(R.id.calenderFragment);
                } else {
                    showSignInDialog();
                }
            } else {
                return false;
            }
            return true;
        });

        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isOnline = networkInfo != null && networkInfo.isConnected();
        if (!isOnline) {
            showNoInternetSnackbar();
            first=!first;
        }
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                showNoInternetSnackbar();
                first=!first;
            }

            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                if (!first) {
                    Snackbar.make(findViewById(R.id.homeFragmentContainerView), "internet connection has been restored",
                            Snackbar.LENGTH_SHORT).show();
                    first =!first;
                }
            }
        });
    }


    private void showNoInternetSnackbar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.homeFragmentContainerView), "No internet connection", Snackbar.LENGTH_SHORT)
                .setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }


    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign In Required")
                .setMessage("You need to sign in to continue. Would you like to sign in now?")
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}