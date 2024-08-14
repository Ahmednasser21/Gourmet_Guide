package com.ahmed.gourmetguide.iti.calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.calender.presenter.CalenderPresenter;
import com.ahmed.gourmetguide.iti.favourite.presenter.FavouritePresenter;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import java.util.List;

public class CalenderFragment extends Fragment implements OnPlanView {
    RecyclerView planRec;
    CalenderPresenter calenderPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        planRec = view.findViewById(R.id.rec_planed_list);
        calenderPresenter = new CalenderPresenter(this, Repository.getInstance(getContext()));
    }

    @Override
    public void onPlanViewSuccess(List<PlanDTO> meals) {

    }

    @Override
    public void onPlanViewFailure(String errMsg) {

    }
}