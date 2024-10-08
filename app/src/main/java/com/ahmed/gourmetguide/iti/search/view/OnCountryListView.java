package com.ahmed.gourmetguide.iti.search.view;

import com.ahmed.gourmetguide.iti.model.remote.CountryDTO;

import java.util.List;

public interface OnCountryListView {
    void onCountryListSuccess(List<CountryDTO> countries);
    void onCountryListFailure(String errorMsg);
}
