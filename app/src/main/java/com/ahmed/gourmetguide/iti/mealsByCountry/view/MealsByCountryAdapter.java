package com.ahmed.gourmetguide.iti.mealsByCountry.view;

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
import com.ahmed.gourmetguide.iti.model.remote.MealsByCountryDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class MealsByCountryAdapter  extends RecyclerView.Adapter<MealsByCountryAdapter.ViewHolder>{
    List<MealsByCountryDTO> meals;
    Context context;
    public MealsByCountryAdapter  (Context context,List<MealsByCountryDTO> meals){
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.public_row_for_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealsByCountryDTO meal =  meals.get(position);

        holder.categoryName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v->{
            NavDirections action = MealsByCountryFragmentDirections.actionMealsByCountryFragmentToMealDetails(meal.getIdMeal());
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

