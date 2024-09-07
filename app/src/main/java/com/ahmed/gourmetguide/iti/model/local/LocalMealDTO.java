package com.ahmed.gourmetguide.iti.model.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favourite_Meals")
public class LocalMealDTO {
    @PrimaryKey
    @NonNull
    String idMeal;
    String strMeal;
    String strMealThumb;

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }
}
