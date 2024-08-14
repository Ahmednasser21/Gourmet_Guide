package com.ahmed.gourmetguide.iti.calender.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.calender.view.OnPlanView;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;

public class CalenderPresenter {
    Repository repo;
    OnPlanView onPlanView;
    private static final String TAG = "CalenderPresenter";

    public CalenderPresenter(OnPlanView onPlanView, Repository repo) {
        this.onPlanView = onPlanView;
        this.repo = repo;
    }
    public void getAllPlans(){
        repo.getAllPlansByDAy().subscribe(new FlowableSubscriber<List<PlanDTO>>() {
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
    public void insertIntoPlanByDAy(PlanDTO plan){
        repo.insertPlanByDay(plan).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: insert into plan successful");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: Failure in plan insertion");
            }
        });
    }
    public void deletePlan(PlanDTO plan ){
        repo.deletePlan(plan).subscribe(new CompletableObserver() {
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


}
