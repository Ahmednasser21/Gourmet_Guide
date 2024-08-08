package com.ahmed.gourmetguide.iti.signup_view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ahmed.gourmetguide.iti.R;


public class SignUpActivity extends AppCompatActivity {

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        navController = Navigation.findNavController(this,R.id.fragmentContainerView);
    }
}