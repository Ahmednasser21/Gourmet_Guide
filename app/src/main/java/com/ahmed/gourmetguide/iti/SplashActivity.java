package com.ahmed.gourmetguide.iti;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, SignIn.class));
            finish();
        }, 3000); // Delay for 3 seconds
    }
}