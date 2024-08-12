package com.ahmed.gourmetguide.iti.meal_details_by_id.meal_presenter;

import com.ahmed.gourmetguide.iti.meal_details_by_id.meal_view.OnMealView;
import com.ahmed.gourmetguide.iti.model.MealResponse;
import com.ahmed.gourmetguide.iti.network.MealByIdCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;

public class MealByIdPresenter implements MealByIdCallBack {
    Repository repo;
    OnMealView onMealView;

    public MealByIdPresenter(OnMealView onMealView, Repository repo) {
        this.onMealView = onMealView;
        this.repo = repo;
    }
    public void getMealById(String mealId){
        repo.getMealById(this,mealId);
    }

    @Override
    public void onMealByIdSuccessResult(MealResponse mealResponse) {
        onMealView.onMealByIdSuccess(mealResponse.getMeals().get(0));
    }

    @Override
    public void onMealByIdFailureResult(String errMsg) {
        onMealView.onMealByIdFailure(errMsg);

    }
}
