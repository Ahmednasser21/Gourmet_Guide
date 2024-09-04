package com.ahmed.gourmetguide.iti.login.view;

public interface LoginView {

    void showEmailError(String message);

    void showPasswordError(String message);

    void showLoginSuccess();

    void showLoginFailure(String message);


}