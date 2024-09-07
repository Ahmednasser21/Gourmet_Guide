package com.ahmed.gourmetguide.iti.search.view;

import com.ahmed.gourmetguide.iti.model.remote.IngredientListDTO;

import java.util.List;

public interface OnIngredientListView {
    void onIngredientListSuccess(List<IngredientListDTO> ingredients);
    void onIngredientListFailure(String errorMsg);
}
