package com.ahmed.gourmetguide.iti.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.PlanDTO;

@Database(entities = {MealDTO.class, PlanDTO.class}, version = 4)
public abstract class MealsLocalDataSource extends RoomDatabase {

    private static MealsLocalDataSource instance = null;

    public abstract FavouriteMealsDAO favouriteMealsDAO();
    public abstract PlanDAO planDAO();

    public static MealsLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealsLocalDataSource.class, "FavouriteDB")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}