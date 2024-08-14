package com.ahmed.gourmetguide.iti.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.favourite.presenter.FavouritePresenter;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements OnFavouriteView, OnDeleteFavoriteListener {
    RecyclerView favRecycler;
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
        favouritePresenter = new FavouritePresenter(Repository.getInstance(getContext()),this);
        favouritePresenter.getAllFavourite();
        favouriteAdapter = new FavouriteAdapter(getContext(),new ArrayList<>(),this);
        favRecycler.setAdapter(favouriteAdapter);
        favRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(MealDTO meal) {

        favouritePresenter.deleteFavourite(meal);
        favouritePresenter.getAllFavourite();
    }

    @Override
    public void onFavoriteViewSuccess(List<MealDTO> meals) {
            favouriteAdapter.updateData(meals);
            favRecycler.setAdapter(favouriteAdapter);
            favRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onFavouriteViewFailure(String errMsg) {
        Log.i(TAG, "onFavouriteViewFailure: "+errMsg);

    }
}