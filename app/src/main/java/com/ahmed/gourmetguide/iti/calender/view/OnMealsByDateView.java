package com.ahmed.gourmetguide.iti.calender.view;

import com.ahmed.gourmetguide.iti.model.local.PlanDTO;

import java.util.List;

public interface OnMealsByDateView {
    void onMealsByDateSuccess(List<PlanDTO> plans);
    void onMealsByDateFail(String errMsg);

}
