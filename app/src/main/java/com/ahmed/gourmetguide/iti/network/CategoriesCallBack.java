package com.ahmed.gourmetguide.iti.network;

public interface CategoriesCallBack {

    void onCategoriesSuccessResult(CategoryResponse categoryResponse);
    void onCategoriesFailureResult(String errMsg);
}
