package com.ahmed.gourmetguide.iti.favourite.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.favourite.view.OnFavouriteView;
import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {
    Repository repo;
    OnFavouriteView onFavouriteView;
    private static final String TAG = "FavouritePresenter";

    public FavouritePresenter(Repository repo, OnFavouriteView onFavouriteView) {
        this.repo = repo;
        this.onFavouriteView = onFavouriteView;
    }

    public void getAllFavourite() {
        repo.getAllFavourite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<LocalMealDTO>>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<LocalMealDTO> mealDTOS) {
                onFavouriteView.onFavoriteViewSuccess(mealDTOS);
            }

            @Override
            public void onError(Throwable t) {
                onFavouriteView.onFavouriteViewFailure(t.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }
    public void deleteFavourite(LocalMealDTO meal){
        repo.deleteFavourite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: delete");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
}
