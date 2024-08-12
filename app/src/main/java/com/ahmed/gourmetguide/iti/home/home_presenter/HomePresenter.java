package com.ahmed.gourmetguide.iti.home.home_presenter;


import com.ahmed.gourmetguide.iti.home.home_view.OnCategoryView;
import com.ahmed.gourmetguide.iti.home.home_view.OnRandomMealView;
import com.ahmed.gourmetguide.iti.network.CategoriesCallBack;
import com.ahmed.gourmetguide.iti.network.CategoryResponse;
import com.ahmed.gourmetguide.iti.network.MealResponse;
import com.ahmed.gourmetguide.iti.network.RandomMealCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;

public class HomePresenter implements RandomMealCallBack, CategoriesCallBack {

    Repository repo;
    OnRandomMealView onRandomMealView;
    OnCategoryView onCategoryView;
    public HomePresenter(Repository repo,OnRandomMealView onRandomMealView,
                         OnCategoryView onCategoryView){
        this.repo =repo;
        this.onRandomMealView = onRandomMealView;
        this.onCategoryView = onCategoryView;
    }
    public void getRandomMeal(){
        repo.getRandomMeal(this);
    }

    @Override
    public void onRMSuccessResult(MealResponse mealResponse) {
        onRandomMealView.onRandomMealSuccess(mealResponse.getMeals().get(0));
    }

    @Override
    public void onRMFailureResult(String errMsg) {
        onRandomMealView.onRandomMealFailure(errMsg);
    }

    public void getCategories(){
        repo.getCategory(this);
    }

    @Override
    public void onCategoriesSuccessResult(CategoryResponse categoryResponse) {
        onCategoryView.onCategorySuccess(categoryResponse.getCategories());
    }

    @Override
    public void onCategoriesFailureResult(String errMsg) {
        onCategoryView.onCategoryFailure(errMsg);
    }


}
