package com.ahmed.gourmetguide.iti.search.search_view;

import com.ahmed.gourmetguide.iti.model.IngredientListDTO;

import java.util.List;

public interface OnIngredientListView {
    void onIngredientListSuccess(List<IngredientListDTO> ingredients);
    void onIngredientListFailure(String errorMsg);
}
