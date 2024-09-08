package com.ahmed.gourmetguide.iti.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.ahmed.gourmetguide.iti.home.view.HomeActivity;
import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.login.presenter.LoginPresenter;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements LoginView {
    private EditText userEmail, userPassword;
    private Button startCooking;
    private LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this, FirebaseAuth.getInstance(),
                getActivity().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userEmail = view.findViewById(R.id.email_login);
        userPassword = view.findViewById(R.id.password_login);
        startCooking = view.findViewById(R.id.btn_start_cooking);

        startCooking.setOnClickListener(v -> {
            presenter.loginUser(userEmail.getText().toString().trim(), userPassword.getText().toString().trim());
        });
    }

    @Override
    public void showEmailError(String message) {
        userEmail.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        userPassword.setError(message);
    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(getActivity(), "Authentication Successful.", Toast.LENGTH_SHORT).show();
         NavDirections action = LoginFragmentDirections.actionLoginFragmentToOnBoardFragment();
         Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void showLoginFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}