package com.ahmed.gourmetguide.iti.model.remote;

import java.util.List;

public class MealsByCountryResponse {
    List<MealsByCountryDTO> meals;

    public List<MealsByCountryDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsByCountryDTO> meals) {
        this.meals = meals;
    }
}
