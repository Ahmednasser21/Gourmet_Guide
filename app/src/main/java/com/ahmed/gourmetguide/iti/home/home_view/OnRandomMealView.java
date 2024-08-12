package com.ahmed.gourmetguide.iti.home.home_view;

import com.ahmed.gourmetguide.iti.model.MealDTO;

public interface OnRandomMealView {
    void onRandomMealSuccess(MealDTO meal);
    void onRandomMealFailure(String msg);

}
