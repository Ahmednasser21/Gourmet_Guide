package com.ahmed.gourmetguide.iti.calender.view;

import com.ahmed.gourmetguide.iti.model.PlanDTO;

import java.util.List;

public interface OnPlanView {
    void onPlanViewSuccess(List<PlanDTO> meals);
    void onPlanViewFailure(String errMsg);
}
