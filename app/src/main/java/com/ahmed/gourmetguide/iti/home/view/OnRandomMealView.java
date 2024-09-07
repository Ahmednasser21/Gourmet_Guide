package com.ahmed.gourmetguide.iti.home.view;

import com.ahmed.gourmetguide.iti.model.remote.MealDTO;

public interface OnRandomMealView {
    void onRandomMealSuccess(MealDTO meal);
    void onRandomMealFailure(String msg);

}
