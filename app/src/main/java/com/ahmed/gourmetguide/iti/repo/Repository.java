package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;

import com.ahmed.gourmetguide.iti.network.CategoriesCallBack;
import com.ahmed.gourmetguide.iti.network.CategoryMealsCallBack;
import com.ahmed.gourmetguide.iti.network.MealByIdCallBack;
import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;
import com.ahmed.gourmetguide.iti.network.RandomMealCallBack;

public class Repository {

    private static Repository instance = null;
    MealRemoteDataSource mealRemoteDataSource;

    private Repository(Context context) {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();
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

}
