package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.CategoryResponse;

public interface CategoriesCallBack {

    void onCategoriesSuccessResult(CategoryResponse categoryResponse);
    void onCategoriesFailureResult(String errMsg);
}
