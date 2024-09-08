package com.ahmed.gourmetguide.iti.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.favourite.presenter.FavouritePresenter;
import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements OnFavouriteView, OnDeleteFavoriteListener {
    RecyclerView favRecycler;
    LottieAnimationView emptyBox;
    FavouritePresenter favouritePresenter;
    FavouriteAdapter favouriteAdapter;
    private static final String TAG = "FavouriteFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRecycler = view.findViewById(R.id.rec_favourite_list);
        emptyBox = view.findViewById(R.id.empty_box_animation_fav);
        emptyBox.setVisibility(View.GONE);
        favouritePresenter = new FavouritePresenter(Repository.getInstance(getContext()),this);
        favouritePresenter.getAllFavourite();
        favouriteAdapter = new FavouriteAdapter(getContext(),new ArrayList<>(),this);
        favRecycler.setAdapter(favouriteAdapter);
        favRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(LocalMealDTO meal) {

        favouritePresenter.deleteFavourite(meal);
        favouritePresenter.getAllFavourite();
    }

    @Override
    public void onFavoriteViewSuccess(List<LocalMealDTO> meals) {
            favouriteAdapter.updateData(meals);
        if (meals.isEmpty()){
            emptyBox.setVisibility(View.VISIBLE);
        }
        else {
            emptyBox.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFavouriteViewFailure(String errMsg) {
        Log.i(TAG, "onFavouriteViewFailure: "+errMsg);

    }
}