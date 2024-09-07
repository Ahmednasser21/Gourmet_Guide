package com.ahmed.gourmetguide.iti.model.remote;

import java.util.List;

public class CategoryResponse {
    public List <CategoryDTO> categories;

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
