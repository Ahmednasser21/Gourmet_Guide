package com.ahmed.gourmetguide.iti.meal_by_ingredient.view;

import com.ahmed.gourmetguide.iti.model.MealByIngredientDTO;

import java.util.List;

public interface OnMealByIngredientView {
    void onMealByIngredientSuccess(List<MealByIngredientDTO> meals);
    void onMealByIngredientFailure(String errorMsg);
}
