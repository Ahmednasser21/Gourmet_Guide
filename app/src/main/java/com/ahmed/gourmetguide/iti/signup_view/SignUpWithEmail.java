package com.ahmed.gourmetguide.iti.signup_view;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.gourmetguide.iti.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpWithEmail extends Fragment {

    EditText displayNameText, emailText, passwordText, rePasswordText;
    TextView nameWarningText, emailWarningText, passwordWarningText, rePasswordWarningText;
    ProgressBar progressBar;
    Button signUp;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        nameWarningText = view.findViewById(R.id.tv_name_warning);
        emailWarningText = view.findViewById(R.id.tv_email_warning);
        passwordWarningText = view.findViewById(R.id.tv_password_warning);
        rePasswordWarningText = view.findViewById(R.id.tv_rePassword_warning);

        progressBar = view.findViewById(R.id.progressBar);
        signUp = view.findViewById(R.id.btn_signUp_email);


        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(v -> {

            nameWarningText.setVisibility(View.GONE);
            emailWarningText.setVisibility(View.GONE);
            passwordWarningText.setVisibility(View.GONE);
            rePasswordWarningText.setVisibility(View.GONE);

            String name, email, password, rePassword;
            name = String.valueOf(displayNameText.getText());
            email = String.valueOf(emailText.getText());
            password = String.valueOf(passwordText.getText());
            rePassword = String.valueOf(rePasswordText.getText());

            if (TextUtils.isEmpty(name) || name.length() < 2) {
                nameWarningText.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(email) || !email.contains("@") || !email.contains(".com")) {
                emailWarningText.setVisibility(View.VISIBLE);
                return;
            } else if (TextUtils.isEmpty(password) || password.length() < 6) {
                passwordWarningText.setVisibility(View.VISIBLE);
                return;
            } else if (TextUtils.isEmpty(rePassword) || !rePassword.equals(password)) {
                rePasswordWarningText.setVisibility(View.VISIBLE);

            } else {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    NavDirections action = SignUpWithEmailDirections.actionSignUpWithEmailToOnBoardFragment();
                                    Navigation.findNavController(v).navigate(action);
                                    Log.d(TAG, "createUserWithEmail:success");

                                } else {

                                    try {
                                        throw task.getException();

                                    } catch (FirebaseAuthUserCollisionException e) {
                                        Log.w(TAG, "createUserWithEmail:failure - Email already in use", e);
                                        showDialog(v);

                                    } catch (Exception e) {
                                        Log.w(TAG, "createUserWithEmail:failure", e);
                                        emailWarningText.setVisibility(View.VISIBLE);
                                    }

                                }
                            }
                        });
            }
        });

    }

    private void showDialog(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Duplicated E-Mail")
                .setMessage("This email is already in use. Please use a different email or click OK to log in ")
                .setPositiveButton("OK", (dialog, id) -> {
                    NavDirections action = SignUpWithEmailDirections.actionSignUpWithEmailToLoginFragment();
                    Navigation.findNavController(v).navigate(action);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}