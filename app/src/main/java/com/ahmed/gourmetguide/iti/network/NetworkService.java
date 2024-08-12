package com.ahmed.gourmetguide.iti.network;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface NetworkService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("categories.php")
    Observable<CategoryResponse> getCategories();
}

