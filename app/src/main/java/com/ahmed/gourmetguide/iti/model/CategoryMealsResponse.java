package com.ahmed.gourmetguide.iti.model;

import java.util.List;

public class CategoryMealsResponse {
    List <CategoryMealsDTO> meals;

    public List<CategoryMealsDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<CategoryMealsDTO> meals) {
        this.meals = meals;
    }
}
