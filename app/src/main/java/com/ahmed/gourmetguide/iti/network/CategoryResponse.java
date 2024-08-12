package com.ahmed.gourmetguide.iti.network;

import java.util.List;

public class CategoryResponse {
    List <CategoryDTO> categories;

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
