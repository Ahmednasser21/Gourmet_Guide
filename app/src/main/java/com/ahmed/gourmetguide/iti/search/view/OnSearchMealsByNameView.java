package com.ahmed.gourmetguide.iti.search.view;

import com.ahmed.gourmetguide.iti.model.remote.MealDTO;

import java.util.List;

public interface OnSearchMealsByNameView {

    void OnSearchMealsByNamSuccess(List<MealDTO>meals);
    void OnSearchMealsByNameViewFailure(String errMsg);
}
