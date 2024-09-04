package com.ahmed.gourmetguide.iti.search.search_view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.model.CategoryDTO;
import com.ahmed.gourmetguide.iti.model.CountryDTO;
import com.ahmed.gourmetguide.iti.model.IngredientListDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.search.search_presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements OnSearchCategoryView , OnIngredientListView
,OnCountryListView{

    SearchPresenter searchPresenter;
    SearchCategoryAdapter searchCategoryAdapter;
    SearchView searchBar;
    Chip categoriesChip, countriesChip, ingredientsChip, mealsChip;
    ChipGroup chipGroup;
    RecyclerView results;
    List<CategoryDTO> categories;
    List<IngredientListDTO> ingredientListDTOS;
    List<CountryDTO> countries;
    IngredientListAdapter ingredientListAdapter;
    CountryListAdapter countryListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        searchPresenter = new SearchPresenter(this, this,this,Repository.getInstance(getContext()));
        searchPresenter.getCategories();
        searchPresenter.getIngredientList();
        searchPresenter.getCountryList();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBar = view.findViewById(R.id.edt_search_bar);
        categoriesChip = view.findViewById(R.id.chip_categories);
        countriesChip = view.findViewById(R.id.chip_country);
        ingredientsChip = view.findViewById(R.id.chip_ingredients);
        mealsChip = view.findViewById(R.id.chip_meals);
        mealsChip.setVisibility(View.GONE);
        chipGroup = view.findViewById(R.id.chipGroup_search);
        results = view.findViewById(R.id.rec_categories_search_fragment);

        results.setLayoutManager(new GridLayoutManager(getContext(), 2));

        searchCategoryAdapter = new SearchCategoryAdapter(getContext(), new ArrayList<>());
        ingredientListAdapter = new IngredientListAdapter(getContext(),new ArrayList<>());
        countryListAdapter = new CountryListAdapter(new ArrayList<>());
        results.setAdapter(searchCategoryAdapter);

        showList();
       searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener()  {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @SuppressLint("CheckResult")
            @Override
            public boolean onQueryTextChange(String searchable) {

                if (categoriesChip.isChecked()) {
                    Flowable.fromIterable(categories)
                            .filter(it -> it.getStrCategory().toLowerCase().contains(searchable.toString()))
                            .toList()
                            .subscribe(fillteredList -> searchCategoryAdapter.UpdatedList(fillteredList));
                } else if (countriesChip.isChecked()) {
                    Flowable.fromIterable(countries)
                            .filter(item -> item.getStrArea().toLowerCase().contains(searchable.toString()))
                            .toList().subscribe(
                                    fillteredList -> countryListAdapter.updateCountries(fillteredList));

                } else if (ingredientsChip.isChecked()) {
                    Flowable.fromIterable(ingredientListDTOS)
                            .filter(item -> item.getStrIngredient().toLowerCase().contains(searchable.toString()))
                            .toList().subscribe(
                                    fillteredList -> ingredientListAdapter.UpdatedList(fillteredList)
                            );

                } else if (mealsChip.isChecked()) {

                }
                return false;
            }
        });
        searchBar.setOnClickListener(v -> {
            searchBar.setIconified(false);
            searchBar.requestFocus();
        });

    }


    @Override
    public void onSearchCategorySuccess(List<CategoryDTO> categories) {
        Log.i(TAG, "onSearchCategorySuccess: "+categories.size());
        this.categories = categories;
        results.setAdapter(new SearchCategoryAdapter(getContext(),categories));
    }

    @Override
    public void onSearchCategoryFailure(String errorMsg) {
    }

    @Override
    public void onIngredientListSuccess(List<IngredientListDTO> ingredients) {
        Log.i(TAG, "onIngredientListSuccess: ");
        this.ingredientListDTOS  = ingredients;
    }

    @Override
    public void onIngredientListFailure(String errorMsg) {
        Log.i("TAG", "onIngredientListFailure: ");

    }

    private void showList() {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {

                    if (chip == categoriesChip && results.getAdapter() != searchCategoryAdapter) {
                        results.setAdapter(searchCategoryAdapter);
                        searchCategoryAdapter.UpdatedList(categories);
                    } else if (chip == countriesChip) {
                        results.setAdapter(countryListAdapter);
                        countryListAdapter.updateCountries(countries);

                    } else if (chip == ingredientsChip) {
                        results.setAdapter(ingredientListAdapter);
                        ingredientListAdapter.UpdatedList(ingredientListDTOS);

                    } else if (chip == mealsChip) {
                    }
                } else {
                    boolean anyChecked = false;
                    for (int j = 0; j < chipGroup.getChildCount(); j++) {
                        Chip otherChip = (Chip) chipGroup.getChildAt(j);
                        if (otherChip.isChecked()) {
                            anyChecked = true;
                            break;
                        }
                    }
                    if (!anyChecked) {
                        results.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public void onCountryListSuccess(List<CountryDTO> countries) {
        this.countries = countries;
        countryListAdapter.updateCountries(countries);
    }

    @Override
    public void onCountryListFailure(String errorMsg) {

    }
}