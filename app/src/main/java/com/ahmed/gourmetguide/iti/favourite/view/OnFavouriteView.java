package com.ahmed.gourmetguide.iti.favourite.view;

import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;

import java.util.List;

public interface OnFavouriteView {
    void onFavoriteViewSuccess(List<LocalMealDTO> meals);
    void onFavouriteViewFailure(String errMsg);
}
