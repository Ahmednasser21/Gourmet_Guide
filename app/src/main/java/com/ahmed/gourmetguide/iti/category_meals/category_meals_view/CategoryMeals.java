package com.ahmed.gourmetguide.iti.category_meals.category_meals_view;

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
import com.ahmed.gourmetguide.iti.category_meals.category_meals_presenter.CategoryMealsPresenter;
import com.ahmed.gourmetguide.iti.model.CategoryMealsDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;

import java.util.List;

public class CategoryMeals extends Fragment implements OnCategoryMealsView {
    CategoryMealsPresenter categoryMealsPresenter;
    CategoryMealsAdapter categoryMealsAdapter;
    TextView categoryName;
    RecyclerView mealsRec;
    String clickedCategoryName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clickedCategoryName = CategoryMealsArgs.fromBundle(getArguments()).getCategoryName();
        categoryMealsPresenter = new CategoryMealsPresenter(this,Repository.getInstance(getContext()));
        categoryMealsPresenter.getCategoryMeals(clickedCategoryName);

        categoryName = view.findViewById(R.id.tv_category_name_cm);
        categoryName.setText(clickedCategoryName);

        mealsRec = view.findViewById(R.id.rec_category_meals);
    }

    @Override
    public void onCategoryMealsSuccess(List<CategoryMealsDTO> meals) {
        categoryMealsAdapter = new CategoryMealsAdapter(getContext(),meals);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        mealsRec.setAdapter(categoryMealsAdapter);
        mealsRec.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onCategoryMealsFailure(String errorMsg) {

    }
}
