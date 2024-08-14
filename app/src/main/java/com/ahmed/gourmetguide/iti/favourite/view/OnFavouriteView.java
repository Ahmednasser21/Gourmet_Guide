package com.ahmed.gourmetguide.iti.favourite.view;

import com.ahmed.gourmetguide.iti.model.MealDTO;

import java.util.List;

public interface OnFavouriteView {
    void onFavoriteViewSuccess(List<MealDTO> meals);
    void onFavouriteViewFailure(String errMsg);
}
