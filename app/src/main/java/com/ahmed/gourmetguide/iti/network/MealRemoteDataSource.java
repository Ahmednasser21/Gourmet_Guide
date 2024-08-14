package com.ahmed.gourmetguide.iti.network;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.facebook.FacebookSdk.getCacheDir;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ahmed.gourmetguide.iti.model.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.model.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.model.MealResponse;
import com.ahmed.gourmetguide.iti.model.MealsByCountryResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
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
                    .header("Cache-Control", "public, max-age=" + 60) // 1 minute
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
                .addNetworkInterceptor(onlineInterceptor)
                .addInterceptor(offlineInterceptor)
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
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://www.google.com");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Test");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setConnectTimeout(1500); // Timeout in milliseconds
            urlConnection.setReadTimeout(1500); // Optional, adds a read timeout
            urlConnection.connect();
            return (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            // Log or handle the exception if needed
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
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

    public void getCategories(CategoriesCallBack categoriesCallBack) {
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

    public void getCategoryMeals(CategoryMealsCallBack categoryMealsCallBack, String categoryName) {
        Single<CategoryMealsResponse> categoryMeals = networkService.getCategoryMeals(categoryName);
        categoryMeals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoryMealsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Random meal ");
                    }

                    @Override
                    public void onSuccess(@NonNull CategoryMealsResponse categoryMealsResponse) {
                        categoryMealsCallBack.onCategoryMealsSuccessResult(categoryMealsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        categoryMealsCallBack.onCategoryMealsFailureResult(e.getMessage());

                    }
                });
    }
    public void getMealByID(MealByIdCallBack mealByIdCallBack,String mealId) {
        Single<MealResponse> randomMeal = networkService.getMealById(mealId);
        randomMeal.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Random meal ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealResponse mealResponse) {
                        mealByIdCallBack.onMealByIdSuccessResult(mealResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        mealByIdCallBack.onMealByIdFailureResult(e.getMessage());

                    }
                });
    }
    public void getIngredientsList(IngredientListCallBack ingredientListCallBack){

        Single<IngredientListResponse> ingredients = networkService.getIngredientList();
        ingredients.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<IngredientListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Ingredients ");
                    }

                    @Override
                    public void onSuccess(@NonNull IngredientListResponse response) {
                        Log.i(TAG, "onSuccess: "+response);
                       ingredientListCallBack.onIngredientListSuccessResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        ingredientListCallBack.onIngredientListFailureResult(e.getMessage());

                    }
                });

    }
    public void getMealByIngredient(MealByIngredientCallBack mealByIngredientCallBack,String ingredient){

        Single<MealByIngredientResponse> ingredients = networkService.getMealsByIngredient(ingredient);
        ingredients.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealByIngredientResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Ingredients ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealByIngredientResponse response) {
                        Log.i(TAG, "onSuccess: "+response);
                        mealByIngredientCallBack.onMealByIngredientSuccessResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        mealByIngredientCallBack.onMealByIngredientFailureResult(e.getMessage());

                    }
                });

    }
    public void getCountryList(CountryListCallBack countryListCallBack){

        Single<CountryListResponse> countries = networkService.getCountryList();
        countries.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CountryListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: countries ");
                    }

                    @Override
                    public void onSuccess(@NonNull CountryListResponse response) {
                        Log.i(TAG, "onSuccess: "+response);
                        countryListCallBack.onCountryListSuccessResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        countryListCallBack.onCountryListFailureResult(e.getMessage());

                    }
                });

    }
    public void getMealByCountry(MealsByCountryCallBack mealsByCountryCallBack, String country){

        Single<MealsByCountryResponse> ingredients = networkService.getMealsByICountry(country);
        ingredients.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MealsByCountryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: Meals ");
                    }

                    @Override
                    public void onSuccess(@NonNull MealsByCountryResponse response) {
                        Log.i(TAG, "onSuccess: "+response);
                       mealsByCountryCallBack.onMealsByCSuccessResult(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        mealsByCountryCallBack.onMealsByCFailureResult(e.getMessage());

                    }
                });

    }
}