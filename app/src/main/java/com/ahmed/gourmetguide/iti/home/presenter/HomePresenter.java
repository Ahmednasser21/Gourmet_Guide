package com.ahmed.gourmetguide.iti.home.presenter;


import android.util.Log;

import com.ahmed.gourmetguide.iti.home.view.OnCategoryView;
import com.ahmed.gourmetguide.iti.home.view.OnRandomMealView;
import com.ahmed.gourmetguide.iti.model.remote.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealResponse;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

    Repository repo;
    OnRandomMealView onRandomMealView;
    OnCategoryView onCategoryView;
    private static final String TAG = "HomePresenter";
    public HomePresenter(Repository repo,OnRandomMealView onRandomMealView,
                         OnCategoryView onCategoryView){
        this.repo =repo;
        this.onRandomMealView = onRandomMealView;
        this.onCategoryView = onCategoryView;
    }
    public void getRandomMeal(){
        repo.getRandomMeal().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Random meal ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealResponse mealResponse) {
                        onRandomMealView.onRandomMealSuccess(mealResponse.getMeals().get(0));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                       onRandomMealView.onRandomMealFailure(e.getMessage());

                    }
                });
    }

    public void getCategories(){
        repo.getCategory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: categories");
                    }

                    @Override
                    public void onNext(CategoryResponse response) {
                        if (response != null && response.categories != null) {
                            onCategoryView.onCategorySuccess(response.getCategories());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onCategoryView.onCategoryFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });;
    }


}
