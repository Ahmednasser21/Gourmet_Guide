package com.ahmed.gourmetguide.iti.signup.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ahmed.gourmetguide.iti.home.home_view.HomeActivity;
import com.ahmed.gourmetguide.iti.R;

public class OnBoardFragment extends Fragment {

    private Button startJourney;
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_login_file_key), MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return inflater.inflate(R.layout.fragment_on_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        startJourney = view.findViewById(R.id.btn_start_journey);
        startJourney.setOnClickListener(v -> {
            editor.putBoolean(getString(R.string.preferences_is_logged_in), true);
            editor.apply();

            startActivity(new Intent(getActivity(), HomeActivity.class));
            requireActivity().finish();
        });
    }
}