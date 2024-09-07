package com.ahmed.gourmetguide.iti.category_meals.view;


import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsDTO;

import java.util.List;

public interface OnCategoryMealsView {
    void onCategoryMealsSuccess(List<CategoryMealsDTO> meals);
    void onCategoryMealsFailure(String errorMsg);
}
