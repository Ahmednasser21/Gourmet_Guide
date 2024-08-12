package com.ahmed.gourmetguide.iti.home.home_view;

import com.ahmed.gourmetguide.iti.network.CategoryDTO;

import java.util.List;

public interface OnCategoryView {
    void onCategorySuccess(List<CategoryDTO> categories);
    void onCategoryFailure(String errorMsg);
}
