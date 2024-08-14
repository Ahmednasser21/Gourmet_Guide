package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.IngredientListResponse;

public interface IngredientListCallBack {
    void onIngredientListSuccessResult(IngredientListResponse ingredients);
    void onIngredientListFailureResult(String errMsg);
}
