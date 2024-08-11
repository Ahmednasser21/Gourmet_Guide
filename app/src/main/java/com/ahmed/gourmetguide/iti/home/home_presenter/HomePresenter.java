package com.ahmed.gourmetguide.iti.home.home_presenter;


import com.ahmed.gourmetguide.iti.home.home_view.OnRandomMealView;
import com.ahmed.gourmetguide.iti.network.MealResponse;
import com.ahmed.gourmetguide.iti.network.NetworkCallback;
import com.ahmed.gourmetguide.iti.repo.Repository;

public class HomePresenter implements NetworkCallback {

    Repository repo;
    OnRandomMealView onRandomMealView;
    public HomePresenter(Repository repo,OnRandomMealView onRandomMealView){
        this.repo =repo;
        this.onRandomMealView = onRandomMealView;
    }
    public void getRandomMeal(){
        repo.makeNetworkCall(this);
    }

    @Override
    public void onSuccessResult(MealResponse mealResponse) {
        onRandomMealView.onRandomMealSuccess(mealResponse.getMeals().get(0));
    }

    @Override
    public void onFailureResult(String errMsg) {
        onRandomMealView.onRandomMealFailure(errMsg);
    }
}
