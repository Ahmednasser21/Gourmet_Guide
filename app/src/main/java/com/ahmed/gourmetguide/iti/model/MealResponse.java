package com.ahmed.gourmetguide.iti.model;

import java.util.List;

public class MealResponse {
    List<MealDTO> meals;

    public List<MealDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDTO> meals) {
        this.meals = meals;
    }
}
