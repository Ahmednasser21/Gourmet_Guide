package com.ahmed.gourmetguide.iti.signup.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmed.gourmetguide.iti.home.home_view.HomeActivity;
import com.ahmed.gourmetguide.iti.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private EditText userEmail, userPassword;
    private Button startCooking;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_login_file_key), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userEmail = view.findViewById(R.id.email_login);
        userPassword = view.findViewById(R.id.password_login);
        startCooking = view.findViewById(R.id.btn_start_cooking);

        startCooking.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Password is required.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "signInWithEmail:success");
                        Toast.makeText(getActivity(), "Authentication Successful.", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.preferences_is_logged_in), true);
                        editor.putBoolean(getString(R.string.preferences_is_guest), false);
                        editor.apply();

                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        requireActivity().finish();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}