package com.ahmed.gourmetguide.iti.meals_by_country.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.meal_by_ingredient.view.MealsByIngredientAdapter;
import com.ahmed.gourmetguide.iti.meal_by_ingredient.view.MealsByIngredientFragmentArgs;
import com.ahmed.gourmetguide.iti.meals_by_country.presenter.MealsByCountryPresenter;
import com.ahmed.gourmetguide.iti.model.MealsByCountryDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import java.util.List;

public class MealsByCountryFragment extends Fragment implements OnMealsByCountryView {

    MealsByCountryPresenter mealsByCountryPresenter;
    RecyclerView mealsRec;
    TextView country;
    MealsByCountryAdapter mealsByCountryAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals_by_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsRec = view.findViewById(R.id.rec_mealByCountry);
        mealsRec.setLayoutManager(new GridLayoutManager(getContext(),2));

        country = view.findViewById(R.id.meal_name_mealByCountry);

        String countryName =MealsByCountryFragmentArgs.fromBundle(getArguments()).getCountry();

        country.setText(countryName);
        mealsByCountryPresenter = new MealsByCountryPresenter(this, Repository.getInstance(getContext()));
        mealsByCountryPresenter.getMealsByCountry(countryName);

    }

    @Override
    public void onMealByCountrySuccess(List<MealsByCountryDTO> meals) {
            mealsByCountryAdapter = new MealsByCountryAdapter(getContext(),meals);
            mealsRec.setAdapter(mealsByCountryAdapter);
    }

    @Override
    public void onMealByCountryFailure(String errorMsg) {

    }
}