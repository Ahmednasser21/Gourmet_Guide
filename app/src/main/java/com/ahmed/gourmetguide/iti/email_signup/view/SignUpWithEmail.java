package com.ahmed.gourmetguide.iti.email_signup.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.email_signup.presenter.SignupWithEmailPresenter;

public class SignUpWithEmail extends Fragment implements SignupWithEmailView {

    EditText displayNameText, emailText, passwordText, rePasswordText;
    ProgressBar progressBar;
    Button signUp;
    SignupWithEmailPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignupWithEmailPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_with_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayNameText = view.findViewById(R.id.edt_user_name);
        emailText = view.findViewById(R.id.edt_email);
        passwordText = view.findViewById(R.id.edt_password);
        rePasswordText = view.findViewById(R.id.edt_re_password);

        progressBar = view.findViewById(R.id.progressBar);
        signUp = view.findViewById(R.id.btn_signUp_email);

        signUp.setOnClickListener(v -> {
            String name = displayNameText.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String rePassword = rePasswordText.getText().toString();
            presenter.handleSignUp(name, email, password, rePassword);
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNameError(String error) {
        displayNameText.setError(error);
    }

    @Override
    public void showEmailError(String error) {
        emailText.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        passwordText.setError(error);
    }

    @Override
    public void showRePasswordError(String error) {
        rePasswordText.setError(error);
    }

    @Override
    public void navigateToOnBoarding() {
        NavDirections action = SignUpWithEmailDirections.actionSignUpWithEmailToOnBoardFragment();
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void navigateToLogin() {
        NavDirections action = SignUpWithEmailDirections.actionSignUpWithEmailToLoginFragment();
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void showEmailInUseError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Duplicated E-Mail")
                .setMessage("This email is already in use. Please use a different email or click OK to log in")
                .setPositiveButton("OK", (dialog, id) -> navigateToLogin())
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}