package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;

import com.ahmed.gourmetguide.iti.database.FavouriteMealsLocalDataSource;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.network.CategoriesCallBack;
import com.ahmed.gourmetguide.iti.network.CategoryMealsCallBack;
import com.ahmed.gourmetguide.iti.network.CountryListCallBack;
import com.ahmed.gourmetguide.iti.network.IngredientListCallBack;
import com.ahmed.gourmetguide.iti.network.MealByIdCallBack;
import com.ahmed.gourmetguide.iti.network.MealByIngredientCallBack;
import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;
import com.ahmed.gourmetguide.iti.network.MealsByCountryCallBack;
import com.ahmed.gourmetguide.iti.network.RandomMealCallBack;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
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

    public void getRandomMeal(RandomMealCallBack randomMealCallBack){
        mealRemoteDataSource.getRandomMeal(randomMealCallBack);
    }

    public void  getCategory(CategoriesCallBack categoriesCallBack){
        mealRemoteDataSource.getCategories(categoriesCallBack);
    }

    public void getCategoryMeals(CategoryMealsCallBack categoryMealsCallBack,String categoryName) {

        mealRemoteDataSource.getCategoryMeals(categoryMealsCallBack,categoryName);
    }

    public void getMealById(MealByIdCallBack mealByIdCallBack,String mealId){
        mealRemoteDataSource.getMealByID(mealByIdCallBack,mealId);
    }

    public void getIngredientList(IngredientListCallBack ingredientListCallBack){
        mealRemoteDataSource.getIngredientsList(ingredientListCallBack);
    }
    public void getMealByIngredient (MealByIngredientCallBack mealByIngredientCallBack,String ingredient){
        mealRemoteDataSource.getMealByIngredient(mealByIngredientCallBack,ingredient);
    }
    public void getCountryList(CountryListCallBack countryListCallBack){
        mealRemoteDataSource.getCountryList(countryListCallBack);
    }
    public void getMealsByCountry (MealsByCountryCallBack mealsByCountryCallBack,String country){
        mealRemoteDataSource.getMealByCountry(mealsByCountryCallBack,country);
    }
    public Completable insertIntoFavourite(MealDTO meal){
        return
        favouriteMealsLocalDataSource.dao().insert(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable updateFavourite(MealDTO meal){
        return
                favouriteMealsLocalDataSource.dao().Update(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable DeleteFavourite(MealDTO meal){
        return
        favouriteMealsLocalDataSource.dao().delete(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Flowable <List<MealDTO>> getAllFavourite(){
        return
        favouriteMealsLocalDataSource.dao().getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable deleteAllFavourite (){
        return
        favouriteMealsLocalDataSource.dao().deleteAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Completable insertPlanByDay(PlanDTO planDTO, int day){
        return
                favouriteMealsLocalDataSource.dao().insertPlanByDAy(planDTO,day)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
//    public Flowable<List<PlanDTO>> getAllPlansByDAy(int day){
//        return
//                favouriteMealsLocalDataSource.dao().getAllPlansByDAy(day)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//    }


}
