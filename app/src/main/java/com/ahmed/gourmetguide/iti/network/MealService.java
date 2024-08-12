package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface MealService {
    @GET("random.php")
    Single<MealResponse> getRandomMeal();
}

