package com.ahmed.gourmetguide.iti.search.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.ahmed.gourmetguide.iti.model.remote.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.remote.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.remote.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealResponse;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.search.view.OnCountryListView;
import com.ahmed.gourmetguide.iti.search.view.OnIngredientListView;
import com.ahmed.gourmetguide.iti.search.view.OnSearchCategoryView;
import com.ahmed.gourmetguide.iti.search.view.OnSearchMealsByNameView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter{

    Repository repo;
    OnSearchCategoryView onSearchCategoryView;
    OnIngredientListView onIngredientListView;
    OnCountryListView onCountryListView;
    OnSearchMealsByNameView onSearchMealsByNameView;

    public SearchPresenter(OnSearchCategoryView onSearchCategoryView,OnIngredientListView onIngredientListView,
                           OnCountryListView onCountryListView,OnSearchMealsByNameView onSearchMealsByNameView,Repository repo) {
        this.onSearchCategoryView = onSearchCategoryView;
        this.onIngredientListView = onIngredientListView;
        this.onCountryListView = onCountryListView;
        this.onSearchMealsByNameView = onSearchMealsByNameView;
        this.repo = repo;
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
                            onSearchCategoryView.onSearchCategorySuccess(response.getCategories());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onSearchCategoryView.onSearchCategoryFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getIngredientList(){
        repo.getIngredientList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<IngredientListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Ingredients ");
                    }

                    @Override
                    public void onSuccess(@NonNull IngredientListResponse response) {
                        Log.i(TAG, "onSuccess: " + response);
                        onIngredientListView.onIngredientListSuccess(response.getIngredients());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        onIngredientListView.onIngredientListFailure(e.getMessage());

                    }
                });
        ;
    }

    public void getCountryList(){
        repo.getCountryList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CountryListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: countries ");
                    }

                    @Override
                    public void onSuccess(@NonNull CountryListResponse response) {
                        Log.i(TAG, "onSuccess: " + response);
                        onCountryListView.onCountryListSuccess(response.getCountries());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        onCountryListView.onCountryListFailure(e.getMessage());

                    }
                });;
    }
    public void searchMealByName(String mealName){
        repo.searchMealByName(mealName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MealResponse mealResponse) {
                        Log.i(TAG, "onSuccess: searchByMeal");
                        onSearchMealsByNameView.OnSearchMealsByNamSuccess(mealResponse.getMeals());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: searchByMeal");
                        onSearchMealsByNameView.OnSearchMealsByNameViewFailure(e.getMessage());
                    }
                });
    }

}
