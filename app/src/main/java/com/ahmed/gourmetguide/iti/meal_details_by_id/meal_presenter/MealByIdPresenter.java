package com.ahmed.gourmetguide.iti.meal_details_by_id.meal_presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.meal_details_by_id.meal_view.OnMealView;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.MealResponse;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealByIdPresenter  {
    Repository repo;
    OnMealView onMealView;
    private static final String TAG = "MealByIdPresenter";

    public MealByIdPresenter(OnMealView onMealView, Repository repo) {
        this.onMealView = onMealView;
        this.repo = repo;
    }
    public void getMealById(String mealId){
        repo.getMealById(mealId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Random meal ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealResponse mealResponse) {
                        onMealView.onMealByIdSuccess(mealResponse.getMeals().get(0));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        onMealView.onMealByIdFailure(e.getMessage());

                    }
                });;
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
