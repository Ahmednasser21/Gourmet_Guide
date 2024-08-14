
package com.ahmed.gourmetguide.iti.profile.presenter;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View view;
    private FirebaseAuth firebaseAuth;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void loadUserInfo() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String imageUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;

            view.showUserInfo(name, email, imageUrl);
        } else {
            view.showError("User not signed in.");
        }
    }

    @Override
    public void signOut() {
        view.showSignOutAlert();  // Show an alert before signing out
    }

    public void confirmSignOut() {
        firebaseAuth.signOut();
        view.onSignOutSuccess();
    }

    @Override
    public void changePassword(String newPassword) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.onPasswordChangeSuccess();
                } else {
                    view.showError("Password change failed.");
                }
            });
        }
    }

    @Override
    public void updateProfilePicture(String imageUrl) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(imageUrl))
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.onProfilePictureUpdateSuccess(imageUrl);
                } else {
                    view.showError("Profile picture update failed.");
                }
            });
        }
    }
}