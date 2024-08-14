package com.ahmed.gourmetguide.iti.home.home_view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ahmed.gourmetguide.iti.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                navController.navigate(R.id.favouriteFragment);
            } else if (itemId == R.id.calender) {
                navController.navigate(R.id.calenderFragment);
            } else {
                return false;
            }
            return true;
        });
    }
}