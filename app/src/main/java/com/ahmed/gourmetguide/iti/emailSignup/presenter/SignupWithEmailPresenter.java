package com.ahmed.gourmetguide.iti.emailSignup.presenter;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ahmed.gourmetguide.iti.emailSignup.view.SignupWithEmailView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static android.content.ContentValues.TAG;

public class SignupWithEmailPresenter {

    private SignupWithEmailView view;
    private FirebaseAuth mAuth;

    public SignupWithEmailPresenter(SignupWithEmailView view) {
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void handleSignUp(String name, String email, String password, String rePassword) {
        if (TextUtils.isEmpty(name) || name.length() < 2) {
            view.showNameError("Display name should be more than 2 characters");
            return;
        }

        if (TextUtils.isEmpty(email) || !email.contains("@") || !email.contains(".com")) {
            view.showEmailError("Please enter a valid Email");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            view.showPasswordError("Password should be more than 6 characters");
            return;
        }

        if (TextUtils.isEmpty(rePassword) || !rePassword.equals(password)) {
            view.showRePasswordError("Must match password");
            return;
        }

        view.showProgress();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.hideProgress();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            view.navigateToOnBoarding();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Log.w(TAG, "createUserWithEmail:failure - Email already in use", e);
                                view.showEmailInUseError();
                            } catch (Exception e) {
                                Log.w(TAG, "createUserWithEmail:failure", e);
                                view.showEmailError("Please enter a valid Email");
                            }
                        }
                    }
                });
    }
}
