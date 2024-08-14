package com.ahmed.gourmetguide.iti.search.search_view;

import com.ahmed.gourmetguide.iti.model.CountryDTO;

import java.util.List;

public interface OnCountryListView {
    void onCountryListSuccess(List<CountryDTO> countries);
    void onCountryListFailure(String errorMsg);
}
