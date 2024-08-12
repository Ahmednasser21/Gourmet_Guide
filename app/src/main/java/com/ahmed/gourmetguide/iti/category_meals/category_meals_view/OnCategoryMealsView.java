package com.ahmed.gourmetguide.iti.category_meals.category_meals_view;


import com.ahmed.gourmetguide.iti.model.CategoryMealsDTO;

import java.util.List;

public interface OnCategoryMealsView {
    void onCategoryMealsSuccess(List<CategoryMealsDTO> meals);
    void onCategoryMealsFailure(String errorMsg);
}
