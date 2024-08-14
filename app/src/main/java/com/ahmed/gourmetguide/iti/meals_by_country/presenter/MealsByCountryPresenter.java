package com.ahmed.gourmetguide.iti.meals_by_country.presenter;

import com.ahmed.gourmetguide.iti.meals_by_country.view.OnMealsByCountryView;
import com.ahmed.gourmetguide.iti.model.MealsByCountryResponse;
import com.ahmed.gourmetguide.iti.network.MealsByCountryCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;

public class MealsByCountryPresenter implements MealsByCountryCallBack {
    Repository repo;
    OnMealsByCountryView onMealsByCountryView;

    public MealsByCountryPresenter(OnMealsByCountryView onMealsByCountryView, Repository repo) {
        this.onMealsByCountryView = onMealsByCountryView;
        this.repo = repo;
    }
    public void getMealsByCountry(String country){
        repo.getMealsByCountry(this,country);
    }

    @Override
    public void onMealsByCSuccessResult(MealsByCountryResponse mealsByCountryResponse) {
        onMealsByCountryView.onMealByCountrySuccess(mealsByCountryResponse.getMeals());
    }

    @Override
    public void onMealsByCFailureResult(String errMsg) {
        onMealsByCountryView.onMealByCountryFailure(errMsg);
    }
}
