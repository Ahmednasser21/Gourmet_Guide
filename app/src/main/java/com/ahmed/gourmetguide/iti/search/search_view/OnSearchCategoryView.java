package com.ahmed.gourmetguide.iti.search.search_view;

import com.ahmed.gourmetguide.iti.model.CategoryDTO;

import java.util.List;

public interface OnSearchCategoryView {
    void onSearchCategorySuccess(List<CategoryDTO> categories);
    void onSearchCategoryFailure(String errorMsg);
}
