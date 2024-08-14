package com.ahmed.gourmetguide.iti.calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.calender.presenter.CalenderPresenter;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import java.util.ArrayList;
import java.util.List;

public class CalenderFragment extends Fragment implements OnPlanView , OnDeletePlanListener {
    RecyclerView planRec;
    CalenderPresenter calenderPresenter;
   PlanAdapter planAdapter;
    private static final String TAG = "CalenderFragment";

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
        calenderPresenter.getAllPlans();
        planAdapter = new PlanAdapter(getContext(),new ArrayList<>(),this);
        planRec.setLayoutManager(new LinearLayoutManager(getContext()));
        planRec.setAdapter(planAdapter);
    }

    @Override
    public void onPlanViewSuccess(List<PlanDTO> meals) {
        planAdapter.updateData(meals);
        planRec.setLayoutManager(new LinearLayoutManager(getContext()));
        planRec.setAdapter(planAdapter);
        Log.i(TAG, "onPlanViewSuccess: delivered successfully"+meals.size());
    }

    @Override
    public void onPlanViewFailure(String errMsg) {
        Log.i(TAG, "onPlanViewFailure: failed to insert into plan ");
    }

    @Override
    public void onDeleteListener(PlanDTO plan) {

        calenderPresenter.deletePlan(plan);
        calenderPresenter.getAllPlans();

    }
}