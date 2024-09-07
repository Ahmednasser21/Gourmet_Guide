package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.model.remote.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.remote.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.remote.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("categories.php")
    Observable<CategoryResponse> getCategories();

    @GET("filter.php")
    Single<CategoryMealsResponse> getCategoryMeals(@Query("c")String categoryName);

    @GET("lookup.php")
    Single<MealResponse>getMealById(@Query("i")String categoryName);

    @GET("list.php?i=list")
    Single<IngredientListResponse>getIngredientList();

    @GET("filter.php")
    Single<MealByIngredientResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("list.php?a=list")
    Single<CountryListResponse> getCountryList();

    @GET("filter.php")
    Single<MealsByCountryResponse> getMealsByICountry(@Query("a") String area);

}

