package com.ahmed.gourmetguide.iti.signup_view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ahmed.gourmetguide.iti.home.home_view.HomeActivity;
import com.ahmed.gourmetguide.iti.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_login_file_key), MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean(getString(R.string.preferences_is_logged_in), false);

        if (loggedIn) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_sign_up);

        NavController navController = Navigation.findNavController(this, R.id.signUpFragmentContainerView);

        if (savedInstanceState == null) {
            navController.navigate(R.id.signUpFragment);
        }
    }
}