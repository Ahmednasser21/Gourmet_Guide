package com.ahmed.gourmetguide.iti.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.model.remote.MealDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViweHolder> {
    List<MealDTO> meals;
    Context context;

    public MealListAdapter(Context context, List<MealDTO> meals) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.public_row_for_search, parent, false);
        return new ViweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViweHolder holder, int position) {

        MealDTO meal = meals.get(position);

        holder.categoryName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v -> {
            NavDirections action = SearchFragmentDirections.actionSearchFragmentToMealDetails(meal.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        if (meals == null) {
            return 0;
        } else {
            return meals.size();
        }
    }

    void UpdatedList(List<MealDTO> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    class ViweHolder extends RecyclerView.ViewHolder {

        View layout;
        CardView row;
        TextView categoryName;
        ImageView categoryImage;

        public ViweHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            row = itemView.findViewById(R.id.cardView_cm_rwo);
            categoryName = itemView.findViewById(R.id.tv_card_meal_name_row_cm);
            categoryImage = itemView.findViewById(R.id.img_card_image_cm_row);
        }
    }
}
