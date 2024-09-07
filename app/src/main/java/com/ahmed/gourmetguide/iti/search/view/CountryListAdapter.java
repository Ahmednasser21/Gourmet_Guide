package com.ahmed.gourmetguide.iti.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.model.remote.CountryDTO;

import java.util.List;


public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder>{

        private List<CountryDTO> countries;

        public CountryListAdapter(List<CountryDTO> countries) {
            this.countries = countries;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.country.setText(countries.get(position).getStrArea());
            holder.country.setOnClickListener(v->{
                NavDirections action = SearchFragmentDirections.actionSearchFragmentToMealsByCountryFragment(countries.get(position).getStrArea());
                Navigation.findNavController(v).navigate(action);
            });
        }

        @Override
        public int getItemCount() {
            return countries.size();
        }

        public void updateCountries(List<CountryDTO> newCountries) {
            this.countries = newCountries;
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView country;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                country = itemView.findViewById(android.R.id.text1);
            }
        }
    }


