package com.ahmed.gourmetguide.iti.mealsByCountry.view;

import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryDTO;

import java.util.List;

public interface OnMealsByCountryView {
    void onMealByCountrySuccess(List<MealsByCountryDTO> meals);
    void onMealByCountryFailure(String errorMsg);
}
