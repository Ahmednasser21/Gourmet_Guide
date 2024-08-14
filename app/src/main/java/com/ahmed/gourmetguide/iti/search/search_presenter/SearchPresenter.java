package com.ahmed.gourmetguide.iti.search.search_presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.ahmed.gourmetguide.iti.model.CategoryResponse;
import com.ahmed.gourmetguide.iti.model.CountryListResponse;
import com.ahmed.gourmetguide.iti.model.IngredientListResponse;
import com.ahmed.gourmetguide.iti.network.CategoriesCallBack;
import com.ahmed.gourmetguide.iti.network.CountryListCallBack;
import com.ahmed.gourmetguide.iti.network.IngredientListCallBack;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.search.search_view.OnCountryListView;
import com.ahmed.gourmetguide.iti.search.search_view.OnIngredientListView;
import com.ahmed.gourmetguide.iti.search.search_view.OnSearchCategoryView;

public class SearchPresenter implements CategoriesCallBack, IngredientListCallBack , CountryListCallBack {

    Repository repo;
    OnSearchCategoryView onSearchCategoryView;
    OnIngredientListView onIngredientListView;
    OnCountryListView onCountryListView;

    public SearchPresenter(OnSearchCategoryView onSearchCategoryView,OnIngredientListView onIngredientListView,
                           OnCountryListView onCountryListView,Repository repo) {
        this.onSearchCategoryView = onSearchCategoryView;
        this.onIngredientListView = onIngredientListView;
        this.onCountryListView = onCountryListView;
        this.repo = repo;
    }

    public void getCategories(){
        repo.getCategory(this);
    }

    @Override
    public void onCategoriesSuccessResult(CategoryResponse categoryResponse) {
        onSearchCategoryView.onSearchCategorySuccess(categoryResponse.categories);
    }

    @Override
    public void onCategoriesFailureResult(String errMsg) {
        onSearchCategoryView.onSearchCategoryFailure(errMsg);
    }
    public void getIngredientList(){
        repo.getIngredientList(this);}

    @Override
    public void onIngredientListSuccessResult(IngredientListResponse ingredientListResponse) {

        onIngredientListView.onIngredientListSuccess(ingredientListResponse.getIngredients());
        Log.i(TAG, "onIngredientListSuccessResult: ");
    }

    @Override
    public void onIngredientListFailureResult(String errMsg) {
        onIngredientListView.onIngredientListFailure(errMsg);
    }

    public void getCountryList(){
        repo.getCountryList(this);
    }

    @Override
    public void onCountryListSuccessResult(CountryListResponse countries) {
        onCountryListView.onCountryListSuccess(countries.getCountries());
    }

    @Override
    public void onCountryListFailureResult(String errMsg) {
        onCountryListView.onCountryListFailure(errMsg);
    }
}
