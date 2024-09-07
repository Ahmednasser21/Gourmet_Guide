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
import com.ahmed.gourmetguide.iti.model.remote.IngredientListDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter <IngredientListAdapter.ViewHolder> {
    List<IngredientListDTO> ingredients;
    Context context;
    private static final String URL = "https://www.themealdb.com/images/ingredients/";
    private static final String END_POINT=".png";

    public IngredientListAdapter(Context context, List<IngredientListDTO> ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_meals_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientListDTO ingredients = this.ingredients.get(position);
        holder.categoryName.setText(ingredients.getStrIngredient());
        Glide.with(context)
                .load(URL+ingredients.getStrIngredient()+END_POINT)
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v->{
            NavDirections action = SearchFragmentDirections.actionSearchFragmentToMealsByIngredientFragment(ingredients.getStrIngredient());
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    void UpdatedList(List<IngredientListDTO> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

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


