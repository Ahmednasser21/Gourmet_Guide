package com.ahmed.gourmetguide.iti.network;

import android.util.Log;

import com.ahmed.gourmetguide.iti.model.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.MealResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
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
    NetworkService networkService;
    private static MealRemoteDataSource mealsRemoteDataSource = null;

    private MealRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        networkService = retrofit.create(NetworkService.class);
    }

    public static MealRemoteDataSource getInstance() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealRemoteDataSource();
        }
        return mealsRemoteDataSource;
    }
    public void getRandomMeal(RandomMealCallBack randomMealCallBack) {
        Single<MealResponse> randomMeal = networkService.getRandomMeal();
        randomMeal.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Random meal ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealResponse mealResponse) {
                        randomMealCallBack.onRMSuccessResult(mealResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        randomMealCallBack.onRMFailureResult(e.getMessage());

                    }
                });
    }
    public void getCategories(CategoriesCallBack categoriesCallBack){
        Observable<CategoryResponse> category = networkService.getCategories();
        category.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: categories");
                    }

                    @Override
                    public void onNext(CategoryResponse response) {
                        if (response != null && response.categories != null) {
                            categoriesCallBack.onCategoriesSuccessResult(response);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        categoriesCallBack.onCategoriesFailureResult(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}
