package com.ahmed.gourmetguide.iti.categoryMeals.view;

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
import com.ahmed.gourmetguide.iti.model.remote.CategoryMealsDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryMealsAdapter extends RecyclerView.Adapter<CategoryMealsAdapter.ViweHolder>{

    List<CategoryMealsDTO> meals;
    Context context;

    public CategoryMealsAdapter(Context context, List<CategoryMealsDTO> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public ViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.public_row_for_search,parent,false);
        return new ViweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViweHolder holder, int position) {

        CategoryMealsDTO meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.mealImage);
        holder.row.setOnClickListener(v->{

            NavDirections action =CategoryMealsFragmentDirections.actionCategoryMealsToMealDetails(meal.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViweHolder extends RecyclerView.ViewHolder{

        View layout;
        CardView row;
        TextView mealName;
        ImageView mealImage;


        public ViweHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            row = itemView.findViewById(R.id.cardView_cm_rwo);
            mealName = itemView.findViewById(R.id.tv_card_meal_name_row_cm);
            mealImage =itemView.findViewById(R.id.img_card_image_cm_row);
        }
    }
}
