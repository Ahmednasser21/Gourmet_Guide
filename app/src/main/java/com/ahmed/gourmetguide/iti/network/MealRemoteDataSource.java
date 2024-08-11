package com.ahmed.gourmetguide.iti.network;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {
    private static final String TAG = "MealRemoteDataSource";
    final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    MealService  mealService;
    private static MealRemoteDataSource mealsRemoteDataSource = null;

    private MealRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealRemoteDataSource getInstance() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealRemoteDataSource();
        }
        return mealsRemoteDataSource;
    }
    public void makeNetworkCall(NetworkCallback networkCallback) {
        Single<MealResponse> randomMeal = mealService.getRandomMeal();
        randomMeal.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealResponse mealResponse) {
                        networkCallback.onSuccessResult(mealResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        networkCallback.onFailureResult(e.getMessage());

                    }
                });
    }
}
