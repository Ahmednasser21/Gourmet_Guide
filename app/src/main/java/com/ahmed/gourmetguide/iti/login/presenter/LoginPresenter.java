package com.ahmed.gourmetguide.iti.login.presenter;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ahmed.gourmetguide.iti.login.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter{
    private final LoginView view;
    private final FirebaseAuth mAuth;
    private final SharedPreferences sharedPreferences;

    public LoginPresenter(LoginView view, FirebaseAuth mAuth, SharedPreferences sharedPreferences) {
        this.view = view;
        this.mAuth = mAuth;
        this.sharedPreferences = sharedPreferences;
    }
    public void loginUser(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            view.showEmailError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.showPasswordError("Password is required.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("is_logged_in", true);
                        editor.putBoolean("is_guest", false);
                        editor.apply();

                        view.showLoginSuccess();
                    } else {
                        view.showLoginFailure("Authentication Failed.");
                    }
                });
    }
}