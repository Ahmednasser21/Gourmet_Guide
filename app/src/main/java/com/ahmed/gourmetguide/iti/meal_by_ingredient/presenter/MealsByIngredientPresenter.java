package com.ahmed.gourmetguide.iti.meal_by_ingredient.presenter;

import com.ahmed.gourmetguide.iti.meal_by_ingredient.view.OnMealByIngredientView;
import com.ahmed.gourmetguide.iti.model.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.network.MealByIngredientCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;

public class MealsByIngredientPresenter implements MealByIngredientCallBack {
    Repository repo;
    OnMealByIngredientView onMealByIngredientView;

    public MealsByIngredientPresenter(OnMealByIngredientView onMealByIngredientView, Repository repo) {
        this.onMealByIngredientView = onMealByIngredientView;
        this.repo = repo;
    }

    public void getMealsByIngredients(String ingredient){
        repo.getMealByIngredient(this,ingredient);
    }

    @Override
    public void onMealByIngredientSuccessResult(MealByIngredientResponse ingredients) {
            onMealByIngredientView.onMealByIngredientSuccess(ingredients.getMeals());
    }

    @Override
    public void onMealByIngredientFailureResult(String errMsg) {
        onMealByIngredientView.onMealByIngredientFailure(errMsg);
    }
}
