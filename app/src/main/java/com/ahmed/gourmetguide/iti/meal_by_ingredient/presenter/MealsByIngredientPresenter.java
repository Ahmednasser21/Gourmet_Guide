package com.ahmed.gourmetguide.iti.meal_by_ingredient.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.meal_by_ingredient.view.OnMealByIngredientView;
import com.ahmed.gourmetguide.iti.model.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsByIngredientPresenter {
    Repository repo;
    OnMealByIngredientView onMealByIngredientView;
    private static final String TAG = "MealsByIngredientPresenter";

    public MealsByIngredientPresenter(OnMealByIngredientView onMealByIngredientView, Repository repo) {
        this.onMealByIngredientView = onMealByIngredientView;
        this.repo = repo;
    }

    public void getMealsByIngredients(String ingredient){
        repo.getMealByIngredient(ingredient) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealByIngredientResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Ingredients ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealByIngredientResponse response) {
                        Log.i(TAG, "onSuccess: " + response);
                        onMealByIngredientView.onMealByIngredientSuccess(response.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        onMealByIngredientView.onMealByIngredientFailure(e.getMessage());

                    }
                });;
    }

}
