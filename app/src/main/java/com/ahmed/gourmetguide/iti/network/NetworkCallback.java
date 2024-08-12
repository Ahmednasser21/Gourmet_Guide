package com.ahmed.gourmetguide.iti.network;

import java.util.List;

public interface NetworkCallback {
    void onSuccessResult(MealResponse mealResponse);
    void onFailureResult(String errMsg);
}
