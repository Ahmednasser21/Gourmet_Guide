package com.ahmed.gourmetguide.iti.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ahmed.gourmetguide.iti.model.MealDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FavouriteMealsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(MealDTO meal);

    @Update
    Completable update(MealDTO meal);

    @Delete
    Completable delete(MealDTO meal);

    @Query("SELECT * FROM favourite_meals")
    Flowable<List<MealDTO>> getAllMeals();

    @Query("DELETE FROM favourite_meals")
    Completable deleteAllMeals();
}