package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;
import com.ahmed.gourmetguide.iti.database.MealsLocalDataSource;
import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.model.remote.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.remote.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.remote.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.model.remote.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryResponse;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repository {

    private static Repository instance = null;
    MealRemoteDataSource mealRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;

    private Repository(Context context) {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();
        mealsLocalDataSource = MealsLocalDataSource.getInstance(context);
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

    public Completable insertIntoFavourite(LocalMealDTO meal) {
        return mealsLocalDataSource.favouriteMealsDAO().insert(meal);
    }

    public Completable updateFavourite(LocalMealDTO meal) {
        return mealsLocalDataSource.favouriteMealsDAO().update(meal);
    }

    public Completable deleteFavourite(LocalMealDTO meal) {
        return mealsLocalDataSource.favouriteMealsDAO().delete(meal);
    }

    public Flowable<List<LocalMealDTO>> getAllFavourite() {
        return mealsLocalDataSource.favouriteMealsDAO().getAllMeals();
    }

    public Completable deleteAllFavourite() {
        return mealsLocalDataSource.favouriteMealsDAO().deleteAllMeals();
    }

    public Completable insertPlanByDay(PlanDTO planDTO) {
        return mealsLocalDataSource.planDAO().insertPlan(planDTO);
    }

    public Flowable<List<PlanDTO>> getAllPlans() {
        return mealsLocalDataSource.planDAO().getAllPlans();
    }

    public Completable deletePlan(PlanDTO plan) {
        return mealsLocalDataSource.planDAO().deletePlan(plan);
    }
    public Flowable<List<PlanDTO>>getMealsByDate(int day,int month , int year){
        return mealsLocalDataSource.planDAO().getPlansByDate(day,month,year);
    }
    public Single<MealResponse>searchMealByName(String mealName){
        return mealRemoteDataSource.searchMealByName(mealName);
    }
    public Completable deleteAllPlans() {
        return mealsLocalDataSource.planDAO().deleteAllPlans();
    }
}
