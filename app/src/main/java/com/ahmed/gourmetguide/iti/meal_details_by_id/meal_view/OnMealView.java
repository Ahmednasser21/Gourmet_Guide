package com.ahmed.gourmetguide.iti.meal_details_by_id.meal_view;

import com.ahmed.gourmetguide.iti.model.MealDTO;

public interface OnMealView {
    void onMealByIdSuccess(MealDTO meal);
    void onMealByIdFailure(String msg);
}
