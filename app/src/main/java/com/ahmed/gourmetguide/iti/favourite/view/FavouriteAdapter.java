package com.ahmed.gourmetguide.iti.favourite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    List<MealDTO> meals;
    Context context;
    OnDeleteFavoriteListener onDeleteFavoriteListener;
    public FavouriteAdapter (Context context,List<MealDTO> meals,OnDeleteFavoriteListener onDeleteFavoriteListener){
        this.meals = meals;
        this.context = context;
        this.onDeleteFavoriteListener = onDeleteFavoriteListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favourite_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealDTO meal =  meals.get(position);

        holder.categoryName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v->{
            NavDirections action = FavouriteFragmentDirections.actionFavouriteFragmentToMealDetails(meal.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });
        holder.deleteFav.setOnClickListener(v->{
            onDeleteFavoriteListener.onClick(meal);
        });
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateData(List<MealDTO> meals){
        this.meals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        CardView row;
        TextView categoryName;
        ImageView categoryImage;
        Button deleteFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            row = itemView.findViewById(R.id.cardView_fav_rwo);
            categoryName = itemView.findViewById(R.id.tv_card_meal_name_row_fav);
            categoryImage = itemView.findViewById(R.id.img_card_image_fav_row);
            deleteFav = itemView.findViewById(R.id.delete_fav);
        }

    }
}

