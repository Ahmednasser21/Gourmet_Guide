package com.ahmed.gourmetguide.iti.network;

import com.ahmed.gourmetguide.iti.model.CountryListResponse;

public interface CountryListCallBack {
    void onCountryListSuccessResult(CountryListResponse countries);
    void onCountryListFailureResult(String errMsg);
}
