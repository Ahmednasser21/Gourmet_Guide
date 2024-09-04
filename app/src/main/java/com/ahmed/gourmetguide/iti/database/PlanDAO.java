package com.ahmed.gourmetguide.iti.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ahmed.gourmetguide.iti.model.PlanDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlanDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertPlan(PlanDTO plan);

    @Update
    Completable updatePlan(PlanDTO plan);

    @Delete
    Completable deletePlan(PlanDTO plan);

    @Query("SELECT * FROM `Plan`")
    Flowable<List<PlanDTO>> getAllPlans();

    @Query("DELETE FROM `Plan`")
    Completable deleteAllPlans();
}