package com.ahmed.gourmetguide.iti;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.gourmetguide.iti.signup_view.SignUpActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
            finish();
        }, 4000); // Delay for 3 seconds
    }
}