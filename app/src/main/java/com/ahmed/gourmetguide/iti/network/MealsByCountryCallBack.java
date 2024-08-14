package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.MealsByCountryResponse;

public interface MealsByCountryCallBack {
    void onMealsByCSuccessResult(MealsByCountryResponse mealsByCountryResponse);
    void onMealsByCFailureResult(String errMsg);
}
