package com.ahmed.gourmetguide.iti.repo;

import android.content.Context;
import android.util.Log;

import com.ahmed.gourmetguide.iti.database.MealsLocalDataSource;
import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsResponse;
import com.ahmed.gourmetguide.iti.model.remote.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.remote.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.remote.IngredientListResponse;
import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.model.remote.MealByIngredientResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealResponse;
import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryResponse;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.network.MealRemoteDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repository {

    private static Repository instance = null;
    private MealRemoteDataSource mealRemoteDataSource;
    private MealsLocalDataSource mealsLocalDataSource;
    private final FirebaseFirestore firestore;
    private final FirebaseAuth firebaseAuth;
    private static final String TAG = "Repository";
    private FirebaseUser user;

    private Repository(Context context) {
        mealRemoteDataSource = MealRemoteDataSource.getInstance();
        mealsLocalDataSource = MealsLocalDataSource.getInstance(context);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public Single<MealResponse> getRandomMeal() {
        return mealRemoteDataSource.getRandomMeal();
    }

    public Observable<CategoryResponse> getCategory() {
        return mealRemoteDataSource.getCategories();
    }

    public Single<CategoryMealsResponse> getCategoryMeals(String categoryName) {

        return mealRemoteDataSource.getCategoryMeals(categoryName);
    }

    public Single<MealResponse> getMealById(String mealId) {
        return mealRemoteDataSource.getMealByID( mealId);
    }

    public Single<IngredientListResponse> getIngredientList() {
        return mealRemoteDataSource.getIngredientsList();
    }

    public Single<MealByIngredientResponse> getMealByIngredient( String ingredient) {
        return mealRemoteDataSource.getMealByIngredient(ingredient);
    }

    public Single<CountryListResponse> getCountryList() {
        return mealRemoteDataSource.getCountryList();
    }

    public Single<MealsByCountryResponse> getMealsByCountry(String country) {
        return mealRemoteDataSource.getMealByCountry( country);
    }

    public Completable insertIntoFavourite(LocalMealDTO meal) {
        return mealsLocalDataSource.favouriteMealsDAO().insert(meal);
    }

    public Completable updateFavourite(LocalMealDTO meal) {
        return mealsLocalDataSource.favouriteMealsDAO().update(meal);
    }

    public Completable deleteFavourite(LocalMealDTO meal) {
        return mealsLocalDataSource.favouriteMealsDAO().delete(meal);
    }

    public Flowable<List<LocalMealDTO>> getAllFavourite() {
        return mealsLocalDataSource.favouriteMealsDAO().getAllMeals();
    }

    public Completable deleteAllFavourite() {
        return mealsLocalDataSource.favouriteMealsDAO().deleteAllMeals();
    }

    public Completable insertPlan(PlanDTO planDTO) {
        return mealsLocalDataSource.planDAO().insertPlan(planDTO);
    }

    public Flowable<List<PlanDTO>> getAllPlans() {
        return mealsLocalDataSource.planDAO().getAllPlans();
    }

    public Completable deletePlan(PlanDTO plan) {
        return mealsLocalDataSource.planDAO().deletePlan(plan);
    }
    public Flowable<List<PlanDTO>>getMealsByDate(int day,int month , int year){
        return mealsLocalDataSource.planDAO().getPlansByDate(day,month,year);
    }
    public Single<MealResponse>searchMealByName(String mealName){
        return mealRemoteDataSource.searchMealByName(mealName);
    }
    public Completable deleteAllPlans() {
        return mealsLocalDataSource.planDAO().deleteAllPlans();
    }


    public Single<List<LocalMealDTO>> getAllFavouriteFireStore() {
        return Single.create(emitter -> {
            user = firebaseAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                firestore.collection("users")
                        .document(userId)
                        .collection("favouriteMeals")
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            List<LocalMealDTO> favourites = new ArrayList<>();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                LocalMealDTO meal = document.toObject(LocalMealDTO.class);
                                favourites.add(meal);
                            }
                            emitter.onSuccess(favourites);
                        })
                        .addOnFailureListener(e -> emitter.onError(e));
            } else {
                emitter.onError(new Exception("No user logged in"));
            }
        });
    }

    public Single<List<PlanDTO>> getAllPlansFireStore() {
        return Single.create(emitter -> {
            user = firebaseAuth.getCurrentUser();
            if (user != null) {
                String userId = user.getUid();
                firestore.collection("users")
                        .document(userId)
                        .collection("mealPlans")
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            List<PlanDTO> plans = new ArrayList<>();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                PlanDTO plan = document.toObject(PlanDTO.class);
                                plans.add(plan);
                            }
                            emitter.onSuccess(plans);
                        })
                        .addOnFailureListener(e -> emitter.onError(e));
            } else {
                emitter.onError(new Exception("No user logged in"));
            }
        });
    }
    public Task<Void> uploadFav(LocalMealDTO meal) {
        WriteBatch batch = firestore.batch();

        if (user != null) {

            DocumentReference ref = firestore.collection("users")
                    .document(user.getUid())
                    .collection("favouriteMeals")
                    .document(meal.getIdMeal());
            batch.set(ref, meal);

            return batch.commit().addOnSuccessListener(aVoid -> {
                        Log.i(TAG, "All favourite meals uploaded successfully.");
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to upload favourite meals", e);
                    });

        } else {
            Log.e(TAG, "No user or no favorite meals to upload.");
            return Tasks.forException(new Exception("No user or no favorite meals to upload."));
        }
    }

    public Task<Void> deleteFavFromFireStore(String mealId) {

        return firestore.collection("users")
                .document(user.getUid())
                .collection("favouriteMeals")
                .document(mealId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, "onSuccess: delete fav success from fireStore");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Log.e(TAG, "onFailure: delete fav" + e.getMessage());
                    }
                });
    }

    public Task<Void> uploadPlan(PlanDTO plan) {
        WriteBatch batch = firestore.batch();
        if (user != null) {
            DocumentReference ref = firestore.collection("users")
                    .document(user.getUid())
                    .collection("mealPlans")
                    .document(plan.getIdMeal());
            batch.set(ref,plan);
            return batch.commit().addOnSuccessListener(v->{
                        Log.i(TAG, "Meal plan uploaded: " +plan.getStrMeal());

                    })
                    .addOnFailureListener(e ->
                            Log.e(TAG, "Failed to upload meal plan", e)
                    );

        } else {
            Log.e(TAG, "No user or no meal plans to upload.");
            return Tasks.forException(new Exception("No user or no favorite meals to upload."));
        }
    }

    public Task<Void> deletePlanFromFireStore(String mealId) {

        return firestore.collection("users")
                .document(user.getUid())
                .collection("mealPlans")
                .document(mealId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, "onSuccess: delete plan success from fireStore");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Log.e(TAG, "onFailure: delete plan" + e.getMessage());
                    }
                });
    }
    public Completable insertFavourites(List<LocalMealDTO> favourites) {
        return Completable.fromAction(() -> mealsLocalDataSource.favouriteMealsDAO().insertFavList(favourites));
    }

    public Completable insertPlans(List<PlanDTO> plans) {
        return Completable.fromAction(() -> mealsLocalDataSource.planDAO().insertPlanList(plans));
    }
}
