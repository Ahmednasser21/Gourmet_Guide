package com.ahmed.gourmetguide.iti.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FavouriteMealsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(LocalMealDTO meal);

    @Update
    Completable update(LocalMealDTO meal);

    @Delete
    Completable delete(LocalMealDTO meal);

    @Query("SELECT * FROM favourite_meals")
    Flowable<List<LocalMealDTO>> getAllMeals();

    @Query("DELETE FROM favourite_meals")
    Completable deleteAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavList(List<LocalMealDTO> favourites);
}