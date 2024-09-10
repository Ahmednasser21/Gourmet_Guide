package com.ahmed.gourmetguide.iti.mealsByCountry.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.mealsByCountry.view.OnMealsByCountryView;
import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryResponse;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsByCountryPresenter {
    Repository repo;
    OnMealsByCountryView onMealsByCountryView;
    private static final String TAG = "MealsByCountryPresenter";

    public MealsByCountryPresenter(OnMealsByCountryView onMealsByCountryView, Repository repo) {
        this.onMealsByCountryView = onMealsByCountryView;
        this.repo = repo;
    }
    public void getMealsByCountry(String country){
        repo.getMealsByCountry(country).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsByCountryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Meals ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealsByCountryResponse response) {
                        Log.i(TAG, "onSuccess: " + response);
                        onMealsByCountryView.onMealByCountrySuccess(response.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        onMealsByCountryView.onMealByCountryFailure(e.getMessage());

                    }
                });;
    }

}
