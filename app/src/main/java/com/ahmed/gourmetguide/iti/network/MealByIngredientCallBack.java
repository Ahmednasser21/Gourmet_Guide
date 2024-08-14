package com.ahmed.gourmetguide.iti.network;


import com.ahmed.gourmetguide.iti.model.MealByIngredientResponse;

public interface MealByIngredientCallBack {
    void onMealByIngredientSuccessResult(MealByIngredientResponse ingredients);
    void onMealByIngredientFailureResult(String errMsg);
}
