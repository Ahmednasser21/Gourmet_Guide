package com.ahmed.gourmetguide.iti.home.home_view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        boolean isGuest = sharedPreferences.getBoolean(getString(R.string.preferences_is_guest),false);
        navController = Navigation.findNavController(this, R.id.homeFragmentContainerView);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.search) {
                if (!isGuest) {
                    navController.navigate(R.id.searchFragment);
                }else {
                    showSignInDialog();
                }

            } else if (itemId == R.id.favourit) {
                if (!isGuest) {
                    navController.navigate(R.id.favouriteFragment);
                }else {
                    showSignInDialog();
                }
            } else if (itemId == R.id.calender) {
                if (!isGuest) {
                    navController.navigate(R.id.calenderFragment);
                }else {
                    showSignInDialog();
                }
            } else {
                return false;
            }
            return true;
        });
    }
    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign In Required")
                .setMessage("You need to sign in to continue. Would you like to sign in now?")
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(HomeActivity.this , SignUpActivity.class));
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