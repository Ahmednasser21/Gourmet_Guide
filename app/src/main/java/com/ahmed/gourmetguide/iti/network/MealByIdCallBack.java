package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.MealResponse;

public interface MealByIdCallBack {
    void onMealByIdSuccessResult(MealResponse mealResponse);
    void onMealByIdFailureResult(String errMsg);
}
