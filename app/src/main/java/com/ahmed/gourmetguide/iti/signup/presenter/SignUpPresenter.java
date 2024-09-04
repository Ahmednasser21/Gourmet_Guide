package com.ahmed.gourmetguide.iti.signup.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.signup.view.SignUpView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class SignUpPresenter {

    private SignUpView view;
    private Context context;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    public SignUpPresenter(SignUpView view, Context context) {
        this.view = view;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        setupGoogleSignIn();
        setupFacebookLogin();
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
    }

    public void handleGoogleSignIn() {
        if (view.isNetworkAvailable()) {
            view.showProgressBar();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            view.startGoogleSignInIntent(signInIntent);
        } else {
            view.showNoInternetSnackbar();
        }
    }

    public void handleFacebookSignIn() {
        if (view.isNetworkAvailable()) {
            LoginManager.getInstance().logInWithReadPermissions(
                    view.getFragment(), Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                    view.showToast("Facebook Login Successful");
                    changeGuestStatus(false);
                }

                @Override
                public void onCancel() {
                    view.showToast("Facebook Login Cancelled");
                }

                @Override
                public void onError(FacebookException exception) {
                    view.showToast("Facebook Login Error: " + exception.getMessage());
                }
            });
        } else {
            view.showNoInternetSnackbar();
        }
    }

    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        view.hideProgressBar();
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuthWithCredential(credential);
        } catch (ApiException e) {
            view.showToast("Signing Up Failed");
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuthWithCredential(credential);
    }

    private void firebaseAuthWithCredential(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        changeGuestStatus(false);
                        view.navigateToOnBoard();
                    } else {
                        view.showToast("Authentication Failed.");
                    }
                });
    }

    public void handleSkipLogin() {
        if (view.isNetworkAvailable()) {
            view.showGuestModeDialog();
        } else {
            view.showNoInternetSnackbar();
        }
    }

    public void confirmGuestMode() {
        changeGuestStatus(true);
        view.navigateToHome();
    }

    private void changeGuestStatus(boolean isGuest) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.preferences_is_guest), isGuest);
        editor.apply();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}