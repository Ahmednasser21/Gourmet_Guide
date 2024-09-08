package com.ahmed.gourmetguide.iti.onboard.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.home.view.HomeActivity;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.onboard.presenter.OnBoardPresenter;

public class OnBoardFragment extends Fragment implements OnBoardView {

    private Button startJourney;
    private OnBoardPresenter presenter;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private static final String TAG = "OnBoardFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository repository = Repository.getInstance(requireContext());
        presenter = new OnBoardPresenter(this, repository);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_on_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_onboard);
        progressBar.setVisibility(View.GONE);
        startJourney = view.findViewById(R.id.btn_start_journey);
        startJourney.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            presenter.loadDataFromFireStore();
        });
    }

    @Override
    public void onDataLoaded() {
        sharedPreferences.edit().putBoolean(getString(R.string.preferences_is_logged_in), true).apply();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), HomeActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e(TAG, "onDataLoadError: "+errorMessage );
    }
}
