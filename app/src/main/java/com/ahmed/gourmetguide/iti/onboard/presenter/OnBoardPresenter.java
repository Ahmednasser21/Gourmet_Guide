package com.ahmed.gourmetguide.iti.onboard.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.onboard.view.OnBoardView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OnBoardPresenter {

    private final OnBoardView view;
    private final Repository repository;
    private static final String TAG = "OnBoardPresenter";

    public OnBoardPresenter(OnBoardView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void loadDataFromFireStore() {

        Single.zip(
                        repository.getAllFavouriteFireStore(),
                        repository.getAllPlansFireStore(),
                        (favourites, plans) -> new DataResult(favourites, plans)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dataResult -> {
                            saveDataToRoom(dataResult.favourites, dataResult.plans);
                            view.onDataLoaded();
                        },
                        throwable -> view.onDataLoadError(throwable.getMessage())
                );
    }

    private void saveDataToRoom(List<LocalMealDTO> favourites, List<PlanDTO> plans) {
        Completable insertFavourites = repository.insertFavourites(favourites)
                .subscribeOn(Schedulers.io());

        Completable insertPlans = repository.insertPlans(plans)
                .subscribeOn(Schedulers.io());

        Completable.mergeArray(insertFavourites, insertPlans)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "All data inserted successfully"),
                        throwable -> Log.e(TAG, "Error inserting data: " + throwable.getMessage())
                );
    }

    private static class DataResult {
        final List<LocalMealDTO> favourites;
        final List<PlanDTO> plans;

        DataResult(List<LocalMealDTO> favourites, List<PlanDTO> plans) {
            this.favourites = favourites;
            this.plans = plans;
        }
    }
}