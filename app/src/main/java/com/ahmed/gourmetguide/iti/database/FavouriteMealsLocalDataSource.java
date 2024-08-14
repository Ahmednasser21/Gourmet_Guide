package com.ahmed.gourmetguide.iti.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.PlanDTO;

@Database(entities = {MealDTO.class, PlanDTO.class},version = 2)
public abstract class FavouriteMealsLocalDataSource extends RoomDatabase {

    private static FavouriteMealsLocalDataSource instance = null;
    public abstract DAO dao();
    public static FavouriteMealsLocalDataSource getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavouriteMealsLocalDataSource.class
                    , "FavouriteDB").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
