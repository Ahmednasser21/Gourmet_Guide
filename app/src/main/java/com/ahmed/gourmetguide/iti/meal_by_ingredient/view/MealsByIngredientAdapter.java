package com.ahmed.gourmetguide.iti.meal_by_ingredient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.model.MealByIngredientDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class MealsByIngredientAdapter extends RecyclerView.Adapter<MealsByIngredientAdapter.ViewHolder> {
    List<MealByIngredientDTO> meals;
    Context context;
    public MealsByIngredientAdapter (Context context,List<MealByIngredientDTO> meals){
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_meals_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         MealByIngredientDTO meal =  meals.get(position);

        holder.categoryName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v->{
            NavDirections action = MealsByIngredientFragmentDirections.actionMealsByIngredientFragmentToMealDetails(meal.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        CardView row;
        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            row = itemView.findViewById(R.id.cardView_cm_rwo);
            categoryName = itemView.findViewById(R.id.tv_card_meal_name_row_cm);
            categoryImage = itemView.findViewById(R.id.img_card_image_cm_row);
        }

    }
}
