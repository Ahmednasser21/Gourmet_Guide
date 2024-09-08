
package com.ahmed.gourmetguide.iti.profile.presenter;

import android.net.Uri;
import android.util.Log;

import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.profile.view.ProfileView;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter {
    private ProfileView view;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private Repository repo;
    private List<LocalMealDTO> favouriteMeals;
    private List<PlanDTO> plans;
    private static final String TAG = "ProfilePresenter";

    public ProfilePresenter(ProfileView view, Repository repo) {
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        this.repo = repo;
        favouriteMeals = new ArrayList<>();
        plans = new ArrayList<>();
        getAllFavouriteMeals();
        getAllPlans();
    }


    public void loadUserInfo() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String imageUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;

            view.showUserInfo(name, email, imageUrl);
        } else {
            view.showError("User not signed in.");
        }
    }


    public void signOut() {
        uploadPlans();
        uploadFav();
        view.showSignOutAlert();
    }

    public void confirmSignOut() {
        firebaseAuth.signOut();
        view.onSignOutSuccess();
    }


    public void changePassword(String newPassword) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.onPasswordChangeSuccess();
                } else {
                    view.showError("Password change failed.");
                }
            });
        }
    }


    public void updateProfilePicture(String imageUrl) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(imageUrl))
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.onProfilePictureUpdateSuccess(imageUrl);
                } else {
                    view.showError("Profile picture update failed.");
                }
            });
        }
    }

    public void getAllFavouriteMeals() {
        repo.getAllFavourite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<LocalMealDTO>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<LocalMealDTO> localMealDTOS) {
                        Log.i(TAG, "onNext: all favorite meals" + localMealDTOS.size());
                        favouriteMeals = localMealDTOS;
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: get all favorite");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: get all favorite");
                    }
                });
    }

    public void getAllPlans() {
        repo.getAllPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<PlanDTO>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<PlanDTO> planDTOS) {
                        Log.i(TAG, "onNext:get plans" + planDTOS.size());
                        plans = planDTOS;
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: get plans");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: get plans");
                    }
                });
    }

    public void uploadFav() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && !favouriteMeals.isEmpty()) {
            String userId = user.getUid();
            for (LocalMealDTO meal : favouriteMeals) {
                firestore.collection("users")
                        .document(userId)
                        .collection("favouriteMeals")
                        .add(meal)
                        .addOnSuccessListener(documentReference ->
                                Log.i(TAG, "Favourite meal uploaded: " + documentReference.getId())
                        )
                        .addOnFailureListener(e ->
                                Log.e(TAG, "Failed to upload favourite meal", e)
                        );
            }
            deleteAllFav();
        } else {
            Log.e(TAG, "No user or no favorite meals to upload.");
        }
    }

    public void uploadPlans() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && !plans.isEmpty()) {
            String userId = user.getUid();
            for (PlanDTO plan : plans) {
                firestore.collection("users")
                        .document(userId)
                        .collection("mealPlans")
                        .add(plan)
                        .addOnSuccessListener(documentReference ->
                                Log.i(TAG, "Meal plan uploaded: " + documentReference.getId())
                        )
                        .addOnFailureListener(e ->
                                Log.e(TAG, "Failed to upload meal plan", e)
                        );
            }
            deleteAllPlans();
        } else {
            Log.e(TAG, "No user or no meal plans to upload.");
        }
    }

    public void deleteAllPlans() {
        repo.deleteAllPlans()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: deleteAllPlans");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public void deleteAllFav() {
        repo.deleteAllFavourite()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: delete all fav");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
    }


}