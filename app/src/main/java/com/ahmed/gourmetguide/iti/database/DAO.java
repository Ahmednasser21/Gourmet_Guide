package com.ahmed.gourmetguide.iti.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.PlanDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(MealDTO meal);

    @Update
    Completable Update(MealDTO meal);

    @Delete
    Completable delete(MealDTO meal);


    @Query("SELECT * FROM favourite_meals")
    Flowable<List<MealDTO>> getAllMeals();

    @Query("DELETE FROM favourite_meals")
    Completable deleteAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertPlanByDAy(PlanDTO plan);

    @Update
    Completable UpdatePlan(PlanDTO meal);

    @Delete
    Completable deletePlan(PlanDTO plan);


    @Query("SELECT * FROM `Plan`")
    Flowable<List<PlanDTO>> getAllPlansByDAy();

    @Query("DELETE FROM `Plan`")
    Completable deleteAllPlans();
}
