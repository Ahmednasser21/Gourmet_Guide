package com.ahmed.gourmetguide.iti.category_meals.category_meals_presenter;

import com.ahmed.gourmetguide.iti.category_meals.category_meals_view.OnCategoryMealsView;
import com.ahmed.gourmetguide.iti.model.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.network.CategoryMealsCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;

public class CategoryMealsPresenter implements CategoryMealsCallBack {
    Repository repo;
    OnCategoryMealsView onCategoryMealsView;

    public CategoryMealsPresenter(OnCategoryMealsView onCategoryMealsView, Repository repo) {
        this.onCategoryMealsView = onCategoryMealsView;
        this.repo = repo;
    }

    public void getCategoryMeals(String categoryName){
        repo.getCategoryMeals(this,categoryName);
    }


    @Override
    public void onCategoryMealsSuccessResult(CategoryMealsResponse categoryMealsResponse) {
        onCategoryMealsView.onCategoryMealsSuccess(categoryMealsResponse.getMeals());
    }

    @Override
    public void onCategoryMealsFailureResult(String errMsg) {
        onCategoryMealsView.onCategoryMealsFailure(errMsg);
    }
}
