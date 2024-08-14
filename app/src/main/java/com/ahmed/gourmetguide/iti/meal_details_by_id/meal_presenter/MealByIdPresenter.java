package com.ahmed.gourmetguide.iti.meal_details_by_id.meal_presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.meal_details_by_id.meal_view.OnMealView;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.MealResponse;
import com.ahmed.gourmetguide.iti.network.MealByIdCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class MealByIdPresenter implements MealByIdCallBack {
    Repository repo;
    OnMealView onMealView;
    private static final String TAG = "MealByIdPresenter";

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

    public void insertIntoFavourite(MealDTO meal) {
        repo.insertIntoFavourite(meal).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: inserted Successfully");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: Error"+e.getMessage());
            }
        });
    }
}
