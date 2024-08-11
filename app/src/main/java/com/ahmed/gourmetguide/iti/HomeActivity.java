package com.ahmed.gourmetguide.iti;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navController = Navigation.findNavController(this,R.id.homeFragmentContainerView);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.home:
//                    navController.navigate(R.id.homeFragment);
//                    break;
//                case R.id.search:
//                    navController.navigate(R.id.searchFragment);
//                    break;
//                case R.id.favourit:
//                    navController.navigate(R.id.favouriteFragment);
//                    break;
//                case R.id.calender:
//                    navController.navigate(R.id.calendarFragment);
//                    break;
//            }
//            return true;
//        });
    }

}