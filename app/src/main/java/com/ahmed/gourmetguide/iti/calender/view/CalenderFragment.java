package com.ahmed.gourmetguide.iti.calender.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.calender.presenter.CalenderPresenter;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalenderFragment extends Fragment implements OnPlanView , OnDeletePlanListener , OnMealsByDateView {
    RecyclerView planRec;
    CalenderPresenter calenderPresenter;
    PlanAdapter planAdapter;
    TextView filterByDate;
    LottieAnimationView emptyBox;
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
        filterByDate = view.findViewById(R.id.data_filter_tv);
        emptyBox = view.findViewById(R.id.empty_box_animation);
        emptyBox.setVisibility(View.GONE);
        calenderPresenter = new CalenderPresenter(this, this,Repository.getInstance(getContext()));
        calenderPresenter.getAllPlans();
        planAdapter = new PlanAdapter(getContext(),new ArrayList<>(),this);
        planRec.setLayoutManager(new LinearLayoutManager(getContext()));
        planRec.setAdapter(planAdapter);
        filterByDate.setOnClickListener(v->{
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year,monthOfYear + 1,dayOfMonth);
                            filterByDate.setText(formattedDate);
                            calenderPresenter.getPlannedMealsByDate(dayOfMonth,monthOfYear,year);

                        }
                    },
                    year, month, day);
            datePickerDialog.show();
        });

    }


    @Override
    public void onPlanViewSuccess(List<PlanDTO> meals) {
        planAdapter.updateData(meals);
        Log.i(TAG, "onPlanViewSuccess: delivered successfully"+meals.size());
        if (meals.isEmpty()){
            emptyBox.setVisibility(View.VISIBLE);
        }
        else {
            emptyBox.setVisibility(View.GONE);
        }
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

    @Override
    public void onMealsByDateSuccess(List<PlanDTO> plans) {
        planAdapter.updateData(plans);
        Log.i(TAG, "onMealsByDateSuccess: ");
        if (plans.isEmpty()){
            emptyBox.setVisibility(View.VISIBLE);
        }
        else {
            emptyBox.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMealsByDateFail(String errMsg) {
        Log.e(TAG, "onMealsByDateFail: "+errMsg);
    }
}