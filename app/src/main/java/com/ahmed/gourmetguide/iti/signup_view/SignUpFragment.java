package com.ahmed.gourmetguide.iti.signup_view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.gourmetguide.iti.R;

public class SignUpFragment extends Fragment {

    TextView signUpWithEmail;
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

        signUpWithEmail = view.findViewById(R.id.tv_signup_email);

        signUpWithEmail.setOnClickListener(v -> {
            NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToSignUpWithEmail();
            Navigation.findNavController(v).navigate(action);
        });

    }
}