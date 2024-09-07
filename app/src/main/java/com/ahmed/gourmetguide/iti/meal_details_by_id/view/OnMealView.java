package com.ahmed.gourmetguide.iti.meal_details_by_id.view;

import com.ahmed.gourmetguide.iti.model.remote.MealDTO;

public interface OnMealView {
    void onMealByIdSuccess(MealDTO meal);
    void onMealByIdFailure(String msg);
}
