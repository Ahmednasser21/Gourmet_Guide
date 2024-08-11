package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;

import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;
import com.ahmed.gourmetguide.iti.network.NetworkCallback;

public class Repository {

    private static Repository instance = null;
    MealRemoteDataSource mealRemoteDataSource;

    private Repository(Context context) {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();

    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public void  makeNetworkCall(NetworkCallback networkCallback){
        mealRemoteDataSource.makeNetworkCall(networkCallback);
    }
}
