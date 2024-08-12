package com.ahmed.gourmetguide.iti.network;


public interface RandomMealCallBack {
    void onRMSuccessResult(MealResponse mealResponse);
    void onRMFailureResult(String errMsg);
}
