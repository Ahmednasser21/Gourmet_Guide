package com.ahmed.gourmetguide.iti.signup.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.home.view.HomeActivity;
import com.ahmed.gourmetguide.iti.signup.presenter.SignUpPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

public class SignUpFragment extends Fragment implements SignUpView {

    private View rootView;
    private TextView signUpWithEmail, goToLogin;
    private ImageButton googleSignUp, facebookSignUp;
    private Button skip;
    private ProgressBar myProgressBar;
    private SignUpPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        presenter = new SignUpPresenter(this, requireContext());

        initViews(view);
        setupClickListeners();
    }

    private void initViews(View view) {
        myProgressBar = view.findViewById(R.id.progressBar_signUp);
        facebookSignUp = view.findViewById(R.id.btn_facebook);
        googleSignUp = view.findViewById(R.id.btn_google);
        signUpWithEmail = view.findViewById(R.id.tv_signup_email);
        goToLogin = view.findViewById(R.id.tv_login);
        skip = view.findViewById(R.id.btn_skip);
    }

    private void setupClickListeners() {
        googleSignUp.setOnClickListener(v -> presenter.handleGoogleSignIn());
        facebookSignUp.setOnClickListener(v -> presenter.handleFacebookSignIn());

        signUpWithEmail.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToSignUpWithEmail();
                Navigation.findNavController(v).navigate(action);
            } else {
                showNoInternetSnackbar();
            }
        });

        goToLogin.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
                Navigation.findNavController(v).navigate(action);
            } else {
                showNoInternetSnackbar();
            }
        });

        skip.setOnClickListener(v -> presenter.handleSkipLogin());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            presenter.handleGoogleSignInResult(task);
        }
    }

    // SignUpView interface methods

    @Override
    public void showProgressBar() {
        myProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        myProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoInternetSnackbar() {
        Snackbar snackbar = Snackbar.make(rootView, "No internet connection", Snackbar.LENGTH_SHORT)
                .setAction("Settings", v -> {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                });
        snackbar.show();
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = ContextCompat.getSystemService(requireContext(), ConnectivityManager.class);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void startGoogleSignInIntent(Intent signInIntent) {
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    public void navigateToOnBoard() {
        NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToOnBoardFragment();
        Navigation.findNavController(rootView).navigate(action);
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        requireActivity().finish();
    }

    @Override
    public void showGuestModeDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Guest Mode")
                .setMessage("You will lose many important features if you proceed as a guest. Do you want to continue?")
                .setPositiveButton("Proceed", (dialog, which) -> presenter.confirmGuestMode())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }
}