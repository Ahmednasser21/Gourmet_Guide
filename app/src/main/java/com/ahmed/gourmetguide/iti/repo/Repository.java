package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;

import com.ahmed.gourmetguide.iti.database.FavouriteMealsLocalDataSource;
import com.ahmed.gourmetguide.iti.model.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.model.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.MealResponse;
import com.ahmed.gourmetguide.iti.model.MealsByCountryResponse;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {

    private static Repository instance = null;
    MealRemoteDataSource mealRemoteDataSource;
    FavouriteMealsLocalDataSource favouriteMealsLocalDataSource;

    private Repository(Context context) {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();
        favouriteMealsLocalDataSource = FavouriteMealsLocalDataSource.getInstance(context);
    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public Single<MealResponse> getRandomMeal() {
        return mealRemoteDataSource.getRandomMeal();
    }

    public Observable<CategoryResponse> getCategory() {
        return mealRemoteDataSource.getCategories();
    }

    public Single<CategoryMealsResponse> getCategoryMeals(String categoryName) {

        return mealRemoteDataSource.getCategoryMeals(categoryName);
    }

    public Single<MealResponse> getMealById(String mealId) {
        return mealRemoteDataSource.getMealByID( mealId);
    }

    public Single<IngredientListResponse> getIngredientList() {
        return mealRemoteDataSource.getIngredientsList();
    }

    public Single<MealByIngredientResponse> getMealByIngredient( String ingredient) {
        return mealRemoteDataSource.getMealByIngredient(ingredient);
    }

    public Single<CountryListResponse> getCountryList() {
        return mealRemoteDataSource.getCountryList();
    }

    public Single<MealsByCountryResponse> getMealsByCountry(String country) {
        return mealRemoteDataSource.getMealByCountry( country);
    }

    public Completable insertIntoFavourite(MealDTO meal) {
        return
                favouriteMealsLocalDataSource.dao().insert(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable updateFavourite(MealDTO meal) {
        return
                favouriteMealsLocalDataSource.dao().Update(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteFavourite(MealDTO meal) {
        return
                favouriteMealsLocalDataSource.dao().delete(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<MealDTO>> getAllFavourite() {
        return
                favouriteMealsLocalDataSource.dao().getAllMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteAllFavourite() {
        return
                favouriteMealsLocalDataSource.dao().deleteAllMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertPlanByDay(PlanDTO planDTO) {
        return
                favouriteMealsLocalDataSource.dao().insertPlanByDAy(planDTO)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<PlanDTO>> getAllPlansByDAy() {
        return
                favouriteMealsLocalDataSource.dao().getAllPlansByDAy()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deletePlan(PlanDTO plan) {
        return
                favouriteMealsLocalDataSource.dao().deletePlan(plan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }


}
