package com.ahmed.gourmetguide.iti.network;


import com.ahmed.gourmetguide.iti.model.MealResponse;

public interface RandomMealCallBack {
    void onRMSuccessResult(MealResponse mealResponse);
    void onRMFailureResult(String errMsg);
}
