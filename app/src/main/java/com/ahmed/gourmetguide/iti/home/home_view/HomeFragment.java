package com.ahmed.gourmetguide.iti.home.home_view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.home.home_presenter.HomePresenter;
import com.ahmed.gourmetguide.iti.network.MealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.bumptech.glide.Glide;

public class HomeFragment extends Fragment implements OnRandomMealView {
    HomePresenter homePresenter;
    ImageView randomMealImg;
    TextView randomMealName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        randomMealImg = view.findViewById(R.id.img_card_image);
        randomMealName = view.findViewById(R.id.tv_card_daily);
        homePresenter = new HomePresenter(Repository.getInstance(getContext()),this);
        homePresenter.getRandomMeal();

    }

    @Override
    public void onRandomMealSuccess(MealDTO meal) {
        randomMealName.setText(meal.getStrMeal());
        Glide.with(getContext()).load(meal.getStrMealThumb())
                .into(randomMealImg);

    }

    @Override
    public void onRandomMealFailure(String msg) {

    }
}