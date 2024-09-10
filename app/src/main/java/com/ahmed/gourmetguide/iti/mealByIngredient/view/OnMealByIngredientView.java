package com.ahmed.gourmetguide.iti.mealByIngredient.view;

import com.ahmed.gourmetguide.iti.model.remote.MealByIngredientDTO;

import java.util.List;

public interface OnMealByIngredientView {
    void onMealByIngredientSuccess(List<MealByIngredientDTO> meals);
    void onMealByIngredientFailure(String errorMsg);
}
