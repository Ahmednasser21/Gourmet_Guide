package com.ahmed.gourmetguide.iti.calender.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.calender.view.OnMealsByDateView;
import com.ahmed.gourmetguide.iti.calender.view.OnPlanView;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenter {
    Repository repo;
    OnPlanView onPlanView;
    OnMealsByDateView onMealsByDateView;
    private static final String TAG = "CalenderPresenter";

    public CalenderPresenter(OnPlanView onPlanView, OnMealsByDateView onMealsByDateView, Repository repo) {
        this.onPlanView = onPlanView;
        this.onMealsByDateView = onMealsByDateView;
        this.repo = repo;
    }
    public void getAllPlans(){
        repo.getAllPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<PlanDTO>>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                Log.i(TAG, "onSubscribe: plans");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(List<PlanDTO> planDTOS) {
                    onPlanView.onPlanViewSuccess(planDTOS);
                Log.i(TAG, "onNext: plans");
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "onError: failed to send all plans");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: plans");
            }
        });
    }
    public void deletePlan(PlanDTO plan ){
        repo.deletePlanFromFireStore(plan.getIdMeal());
        repo.deletePlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
    public void getPlannedMealsByDate(int day , int month , int year){
        repo.getMealsByDate(day,month,year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<PlanDTO>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<PlanDTO> plans) {
                        onMealsByDateView.onMealsByDateSuccess(plans);
                    }

                    @Override
                    public void onError(Throwable t) {
                       onMealsByDateView.onMealsByDateFail(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
