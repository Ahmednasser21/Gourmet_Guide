package com.ahmed.gourmetguide.iti.model;

import java.util.List;

public class MealByIngredientResponse {
    List <MealByIngredientDTO> meals;

    public List<MealByIngredientDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealByIngredientDTO> meals) {
        this.meals = meals;
    }
}
