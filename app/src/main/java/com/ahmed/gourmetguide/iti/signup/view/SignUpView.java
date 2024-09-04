package com.ahmed.gourmetguide.iti.signup.view;

import android.content.Intent;

import androidx.fragment.app.Fragment;

public interface SignUpView {

    void showProgressBar();
    void hideProgressBar();
    void showToast(String message);
    void showNoInternetSnackbar();
    boolean isNetworkAvailable();
    void startGoogleSignInIntent(Intent signInIntent);
    void navigateToOnBoard();
    void navigateToHome();
    void showGuestModeDialog();
    Fragment getFragment();
}
