package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.CategoryMealsResponse;
public interface CategoryMealsCallBack {

    void onCategoryMealsSuccessResult(CategoryMealsResponse categoryMealsResponse);
    void onCategoryMealsFailureResult(String errMsg);
}
