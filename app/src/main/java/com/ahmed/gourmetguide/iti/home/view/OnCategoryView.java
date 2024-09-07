package com.ahmed.gourmetguide.iti.home.view;

import com.ahmed.gourmetguide.iti.model.remote.CategoryDTO;

import java.util.List;

public interface OnCategoryView {
    void onCategorySuccess(List<CategoryDTO> categories);
    void onCategoryFailure(String errorMsg);
}
