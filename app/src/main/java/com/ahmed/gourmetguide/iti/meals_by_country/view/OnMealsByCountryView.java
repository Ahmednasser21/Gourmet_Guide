package com.ahmed.gourmetguide.iti.meals_by_country.view;

import com.ahmed.gourmetguide.iti.model.MealsByCountryDTO;

import java.util.List;

public interface OnMealsByCountryView {
    void onMealByCountrySuccess(List<MealsByCountryDTO> meals);
    void onMealByCountryFailure(String errorMsg);
}
