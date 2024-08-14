package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;

import com.ahmed.gourmetguide.iti.network.CategoriesCallBack;
import com.ahmed.gourmetguide.iti.network.CategoryMealsCallBack;
import com.ahmed.gourmetguide.iti.network.CountryListCallBack;
import com.ahmed.gourmetguide.iti.network.IngredientListCallBack;
import com.ahmed.gourmetguide.iti.network.MealByIdCallBack;
import com.ahmed.gourmetguide.iti.network.MealByIngredientCallBack;
import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;
import com.ahmed.gourmetguide.iti.network.MealsByCountryCallBack;
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

}
