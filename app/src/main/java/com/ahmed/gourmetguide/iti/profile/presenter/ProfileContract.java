package com.ahmed.gourmetguide.iti.profile.presenter;

public interface ProfileContract {
    interface View {
        void showUserInfo(String name, String email, String imageUrl);
        void showError(String message);
        void onSignOutSuccess();
        void onPasswordChangeSuccess();
        void onProfilePictureUpdateSuccess(String imageUrl);
        void showSignOutAlert();
        void showChangePasswordAlert();
        void showChooseImage();
    }

    interface Presenter {
        void loadUserInfo();
        void signOut();
        void changePassword(String newPassword);
        void updateProfilePicture(String imageUrl);
    }
}

