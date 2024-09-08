
package com.ahmed.gourmetguide.iti.profile.presenter;

import android.net.Uri;
import android.util.Log;

import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.profile.view.ProfileView;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

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
    private Repository repo;
    private static final String TAG = "ProfilePresenter";

    public ProfilePresenter(ProfileView view, Repository repo) {
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
        this.repo = repo;
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
        view.showSignOutAlert();
    }

    public void confirmSignOut() {
        deleteAllPlans();
        deleteAllFav();
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


    public void deleteAllPlans() {
        repo.deleteAllPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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