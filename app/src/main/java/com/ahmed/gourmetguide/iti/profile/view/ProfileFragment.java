package com.ahmed.gourmetguide.iti.profile.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.profile.presenter.ProfileContract;
import com.ahmed.gourmetguide.iti.profile.presenter.ProfilePresenter;
import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.bumptech.glide.Glide;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private static final int PICK_IMAGE = 100;
    private ProfilePresenter presenter;
    private ImageView userImage;
    private TextView userName, e_mail,changePasswordShow;
    private Button signOut, confirm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText changePassword, confirmPassword;
    boolean isShowed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProfilePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userImage = view.findViewById(R.id.profile_image_profile);
        userName = view.findViewById(R.id.user_name_profile);
        e_mail = view.findViewById(R.id.e_mail_profile);
        signOut = view.findViewById(R.id.sign_out);
        changePassword = view.findViewById(R.id.edt_enter_new_password);
        confirmPassword = view.findViewById(R.id.edt_confirm_new_Password);
        confirm = view.findViewById(R.id.btn_confirm_change_password);
        changePassword.setVisibility(View.INVISIBLE);
        confirmPassword.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.INVISIBLE);
        changePasswordShow = view.findViewById(R.id.change_password_show);
        changePasswordShow.setOnClickListener(v->{
            if (!isShowed){
            changePassword.setVisibility(View.VISIBLE);
            confirmPassword.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            isShowed = !isShowed;
            }
            else{
                changePassword.setVisibility(View.INVISIBLE);
                confirmPassword.setVisibility(View.INVISIBLE);
                confirm.setVisibility(View.INVISIBLE);
                isShowed = !isShowed;
            }
        });
        presenter.loadUserInfo();

        signOut.setOnClickListener(v -> presenter.signOut());

        userImage.setOnClickListener(v -> showChooseImage());

        confirm.setOnClickListener(v -> {
            String newPass = changePassword.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();
            if (newPass.equals(confirmPass)) {
                presenter.changePassword(newPass);
            } else {
                showError("Passwords do not match.");
            }
        });
    }

    @Override
    public void showUserInfo(String name, String email, String imageUrl) {
        userName.setText(name);
        e_mail.setText(email);
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(userImage);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignOutSuccess() {
        Toast.makeText(getContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_login_file_key), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.preferences_is_logged_in), false);
        editor.apply();
        startActivity(new Intent(getContext(), SignUpActivity.class));
        getActivity().finish();
    }

    @Override
    public void onPasswordChangeSuccess() {
        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfilePictureUpdateSuccess(String imageUrl) {
        Glide.with(this).load(imageUrl).into(userImage);
        Toast.makeText(getContext(), "Profile picture updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSignOutAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog, which) -> presenter.confirmSignOut())
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void showChangePasswordAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("Change Password")
                .setMessage("Are you sure you want to change your password?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    String newPassword = changePassword.getText().toString().trim();
                    presenter.changePassword(newPassword);
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void showChooseImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            userImage.setImageURI(imageUri);
            presenter.updateProfilePicture(imageUri.toString());  // Upload the selected image to Firebase and update the profile picture
        }
    }
}