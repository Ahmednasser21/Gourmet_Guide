package com.ahmed.gourmetguide.iti.mealByIngredient.view;

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
import com.ahmed.gourmetguide.iti.mealByIngredient.presenter.MealsByIngredientPresenter;
import com.ahmed.gourmetguide.iti.model.remote.MealByIngredientDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import java.util.List;

public class MealsByIngredientFragment extends Fragment implements OnMealByIngredientView {

    MealsByIngredientPresenter mealsByIngredientPresenter;
    RecyclerView mealsRec;
    TextView ingredient;
    MealsByIngredientAdapter mealsByIngredientAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals_by_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsRec = view.findViewById(R.id.rec_mealByIngredient);
        mealsRec.setLayoutManager(new GridLayoutManager(getContext(),2));

        ingredient = view.findViewById(R.id.meal_name_mealByIngredient);

       String ingredientName =  MealsByIngredientFragmentArgs.fromBundle(getArguments()).getIngredient();

       ingredient.setText(ingredientName);

       mealsByIngredientPresenter= new MealsByIngredientPresenter(this, Repository.getInstance(getContext()));
       mealsByIngredientPresenter.getMealsByIngredients(ingredientName);


    }

    @Override
    public void onMealByIngredientSuccess(List<MealByIngredientDTO> meals) {
        mealsByIngredientAdapter = new MealsByIngredientAdapter(getContext(),meals);
        mealsRec.setAdapter(mealsByIngredientAdapter);

    }

    @Override
    public void onMealByIngredientFailure(String errorMsg) {

    }
}