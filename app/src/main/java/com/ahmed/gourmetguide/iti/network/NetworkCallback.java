package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.MealResponse;

public interface NetworkCallback {
    void onSuccessResult(MealResponse mealResponse);
    void onFailureResult(String errMsg);
}
