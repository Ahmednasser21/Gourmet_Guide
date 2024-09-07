package com.ahmed.gourmetguide.iti.category_meals.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.category_meals.view.OnCategoryMealsView;
import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryMealsPresenter  {
    Repository repo;
    OnCategoryMealsView onCategoryMealsView;
    private static final String TAG = "CategoryMealsPresenter";

    public CategoryMealsPresenter(OnCategoryMealsView onCategoryMealsView, Repository repo) {
        this.onCategoryMealsView = onCategoryMealsView;
        this.repo = repo;
    }

    public void getCategoryMeals(String categoryName){
        repo.getCategoryMeals(categoryName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoryMealsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Random meal ");
                    }

                    @Override
                    public void onSuccess(@NonNull CategoryMealsResponse categoryMealsResponse) {
                        onCategoryMealsView.onCategoryMealsSuccess(categoryMealsResponse.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        onCategoryMealsView.onCategoryMealsFailure(e.getMessage());

                    }
                });;
    }
}
