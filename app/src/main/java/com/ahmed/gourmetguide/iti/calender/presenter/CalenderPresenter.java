package com.ahmed.gourmetguide.iti.calender.presenter;

import android.util.Log;

import com.ahmed.gourmetguide.iti.calender.view.OnPlanView;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class CalenderPresenter {
    Repository repo;
    OnPlanView onPlanView;
    private static final String TAG = "CalenderPresenter";

    public CalenderPresenter(OnPlanView onPlanView, Repository repo) {
        this.onPlanView = onPlanView;
        this.repo = repo;
    }
    public void insertIntoPlanByDAy(PlanDTO plan, int day){
        repo.insertPlanByDay(plan,day).subscribe(new CompletableObserver() {
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


}
