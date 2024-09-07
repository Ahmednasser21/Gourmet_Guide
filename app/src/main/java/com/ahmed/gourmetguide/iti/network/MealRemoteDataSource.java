package com.ahmed.gourmetguide.iti.network;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getCacheDir;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.model.remote.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.remote.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.remote.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryResponse;

import java.io.IOException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {
    private static final String TAG = "MealRemoteDataSource";
    final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    NetworkService networkService;
    private static MealRemoteDataSource mealsRemoteDataSource = null;
    Interceptor onlineInterceptor = new Interceptor() {
        @androidx.annotation.NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 30)
                    .build();
        }
    };

    Interceptor offlineInterceptor = new Interceptor() {
        @androidx.annotation.NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetworkAvailable()) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                        .build();
            }
            return chain.proceed(request);
        }
    };


    private MealRemoteDataSource() {
        Cache cache = new Cache(getCacheDir(), 10 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        networkService = retrofit.create(NetworkService.class);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static MealRemoteDataSource getInstance() {
        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealRemoteDataSource();
        }
        return mealsRemoteDataSource;
    }

    public Single<MealResponse> getRandomMeal() {
        return networkService.getRandomMeal();
    }

    public Observable<CategoryResponse> getCategories() {
        return networkService.getCategories();
    }

    public Single<CategoryMealsResponse> getCategoryMeals(String categoryName) {
        return networkService.getCategoryMeals(categoryName);
    }

    public Single<MealResponse> getMealByID(String mealId) {
        return networkService.getMealById(mealId);
    }

    public Single<IngredientListResponse> getIngredientsList() {

        return networkService.getIngredientList();
    }

    public Single<MealByIngredientResponse> getMealByIngredient(String ingredient) {

        return networkService.getMealsByIngredient(ingredient);
    }

    public Single<CountryListResponse> getCountryList() {

        return networkService.getCountryList();
    }

    public Single<MealsByCountryResponse> getMealByCountry(String country) {

        return networkService.getMealsByICountry(country);

    }
    public Single<MealResponse>searchMealByName(String mealName){
        return networkService.SearchMealByName(mealName);
    }
}
