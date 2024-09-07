package com.ahmed.gourmetguide.iti.search.view;

import com.ahmed.gourmetguide.iti.model.remote.CategoryDTO;

import java.util.List;

public interface OnSearchCategoryView {
    void onSearchCategorySuccess(List<CategoryDTO> categories);
    void onSearchCategoryFailure(String errorMsg);
}
