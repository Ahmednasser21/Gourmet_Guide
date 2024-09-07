package com.ahmed.gourmetguide.iti.profile.view;

public interface ProfileView {
    void showUserInfo(String name, String email, String imageUrl);

    void showError(String message);

    void onSignOutSuccess();

    void onPasswordChangeSuccess();

    void onProfilePictureUpdateSuccess(String imageUrl);

    void showSignOutAlert();

    void showChangePasswordAlert();

    void showChooseImage();

}

