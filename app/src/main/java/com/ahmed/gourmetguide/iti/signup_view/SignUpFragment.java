package com.ahmed.gourmetguide.iti.signup_view;

import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.gourmetguide.iti.home.home_view.HomeActivity;
import com.ahmed.gourmetguide.iti.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;


public class SignUpFragment extends Fragment {

    View rootView;
    TextView signUpWithEmail, goToLogin;
    ImageButton googleSignUp, facebookSignUp;
    Button skip;
    ProgressBar myProgressBar;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        myProgressBar = view.findViewById(R.id.progressBar_signUp);
        facebookSignUp = view.findViewById(R.id.btn_facebook);
        googleSignUp = view.findViewById(R.id.btn_google);
        setupFacebookLogin();

        googleSignUp.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                myProgressBar.setVisibility(View.VISIBLE);
                signIn();
            } else {
                showNoInternetSnackbar(v);
            }
        });

        signUpWithEmail = view.findViewById(R.id.tv_signup_email);
        signUpWithEmail.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToSignUpWithEmail();
                Navigation.findNavController(v).navigate(action);
            } else {
                showNoInternetSnackbar(v);
            }
        });

        goToLogin = view.findViewById(R.id.tv_login);
        goToLogin.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment();
                Navigation.findNavController(v).navigate(action);
            } else {
                showNoInternetSnackbar(v);
            }
        });
        skip = view.findViewById(R.id.btn_skip);
        skip.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Guest Mode")
                        .setMessage("You will lose many important features if you proceed as a guest. Do you want to continue?")
                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putBoolean(getString(R.string.preferences_is_guest), true);
                                editor.apply();

                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                requireActivity().finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }else{
                showNoInternetSnackbar(v);
            }
        });
    }

    private void setupFacebookLogin() {
        AppEventsLogger.activateApp(getActivity().getApplication());
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToOnBoardFragment();
            Navigation.findNavController(rootView).navigate(action);
        } else {
            facebookSignUp.setOnClickListener(v -> {
                if (isNetworkAvailable()) {
                    LoginManager.getInstance().logInWithReadPermissions(
                            this, Arrays.asList("public_profile", "email"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            handleFacebookAccessToken(loginResult.getAccessToken());
                            Toast.makeText(getActivity(), "Facebook Login Successful", Toast.LENGTH_SHORT).show();
                            changeGuest();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(getActivity(), "Facebook Login Cancelled", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(getActivity(), "Facebook Login Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    showNoInternetSnackbar(v);
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        myProgressBar.setVisibility(View.GONE);
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToOnBoardFragment();
                            Navigation.findNavController(rootView).navigate(action);
                            changeGuest();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getActivity(), "Signing Up Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToOnBoardFragment();
                        Navigation.findNavController(rootView).navigate(action);
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void changeGuest() {
       SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.preferences_is_guest), false);
        editor.apply();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = ContextCompat.getSystemService(requireContext(), ConnectivityManager.class);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoInternetSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT)
                .setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }

}