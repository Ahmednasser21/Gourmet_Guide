package com.ahmed.gourmetguide.iti.model.remote;

import java.util.List;

public class IngredientListResponse {
    List <IngredientListDTO> meals;

    public List<IngredientListDTO> getIngredients() {
        return meals;
    }

    public void setIngredients(List<IngredientListDTO> ingredients) {
        this.meals = ingredients;
    }
}
