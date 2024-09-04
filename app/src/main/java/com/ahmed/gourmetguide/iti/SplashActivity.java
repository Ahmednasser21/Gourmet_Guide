package com.ahmed.gourmetguide.iti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
            finish();
        }, 3000);
    }
}