package com.ahmed.gourmetguide.iti.email_signup.view;

public interface SignupWithEmailView {
    void showProgress();
    void hideProgress();
    void showNameError(String error);
    void showEmailError(String error);
    void showPasswordError(String error);
    void showRePasswordError(String error);
    void navigateToOnBoarding();
    void navigateToLogin();
    void showEmailInUseError();
}
