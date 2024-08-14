package com.ahmed.gourmetguide.iti.model;

import java.util.List;

public class CountryListResponse {

    List <CountryDTO> meals;

    public List<CountryDTO> getCountries() {
        return meals;
    }

    public void setCountries(List<CountryDTO> meals) {
        this.meals = meals;
    }
}
