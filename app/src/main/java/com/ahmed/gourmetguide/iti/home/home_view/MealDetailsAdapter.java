package com.ahmed.gourmetguide.iti.home.home_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.model.IngredientDTO;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder> {

    MealDTO meal;
    Context context;
    List<IngredientDTO> ingredients;
    IngredientDTO ingredient;

    public MealDetailsAdapter(Context context, MealDTO meal) {
        this.meal = meal;
        this.context = context;
        ingredients = makeIngredientList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_details_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientMeasure.setText(ingredient.getMeasure());
        Glide.with(context)
                .load(ingredient.getImgURL())
                .into(holder.ingredientImage);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ConstraintLayout row;
        TextView ingredientName, ingredientMeasure;
        ImageView ingredientImage;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = itemView;
            row = itemView.findViewById(R.id.cons_meal_details_row);
            ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
            ingredientMeasure = itemView.findViewById(R.id.tv_ingredient_measure);
            ingredientImage = itemView.findViewById(R.id.img_ingredient);

        }

    }

    public List<IngredientDTO> makeIngredientList() {
        List<IngredientDTO> ingredients = new ArrayList<>();

        if (meal.getStrIngredient1() != null && !meal.getStrIngredient1().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient1() + "-Small.png",
                    meal.getStrMeasure1(), meal.getStrIngredient1()));
        }
        if (meal.getStrIngredient2() != null && !meal.getStrIngredient2().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient2() + "-Small.png",
                    meal.getStrMeasure2(), meal.getStrIngredient2()));
        }
        if (meal.getStrIngredient3() != null && !meal.getStrIngredient3().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient3() + "-Small.png",
                    meal.getStrMeasure3(), meal.getStrIngredient3()));
        }
        if (meal.getStrIngredient4() != null && !meal.getStrIngredient4().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient4() + "-Small.png",
                    meal.getStrMeasure4(), meal.getStrIngredient4()));
        }
        if (meal.getStrIngredient5() != null && !meal.getStrIngredient5().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient5() + "-Small.png",
                    meal.getStrMeasure5(), meal.getStrIngredient5()));
        }
        if (meal.getStrIngredient6() != null && !meal.getStrIngredient6().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient6() + "-Small.png",
                    meal.getStrMeasure6(), meal.getStrIngredient6()));
        }
        if (meal.getStrIngredient7() != null && !meal.getStrIngredient7().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient7() + "-Small.png",
                    meal.getStrMeasure7(), meal.getStrIngredient7()));
        }
        if (meal.getStrIngredient8() != null && !meal.getStrIngredient8().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient8() + "-Small.png",
                    meal.getStrMeasure8(), meal.getStrIngredient8()));
        }
        if (meal.getStrIngredient9() != null && !meal.getStrIngredient9().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient9() + "-Small.png",
                    meal.getStrMeasure9(), meal.getStrIngredient9()));
        }
        if (meal.getStrIngredient10() != null && !meal.getStrIngredient10().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient10() + "-Small.png",
                    meal.getStrMeasure10(), meal.getStrIngredient10()));
        }
        if (meal.getStrIngredient11() != null && !meal.getStrIngredient11().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient11() + "-Small.png",
                    meal.getStrMeasure11(), meal.getStrIngredient11()));
        }
        if (meal.getStrIngredient12() != null && !meal.getStrIngredient12().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient12() + "-Small.png",
                    meal.getStrMeasure12(), meal.getStrIngredient12()));
        }
        if (meal.getStrIngredient13() != null && !meal.getStrIngredient13().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient13() + "-Small.png",
                    meal.getStrMeasure13(), meal.getStrIngredient13()));
        }
        if (meal.getStrIngredient14() != null && !meal.getStrIngredient14().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient14() + "-Small.png",
                    meal.getStrMeasure14(), meal.getStrIngredient14()));
        }
        if (meal.getStrIngredient15() != null && !meal.getStrIngredient15().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient15() + "-Small.png",
                    meal.getStrMeasure15(), meal.getStrIngredient15()));
        }
        if (meal.getStrIngredient16() != null && !meal.getStrIngredient16().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient16() + "-Small.png",
                    meal.getStrMeasure16(), meal.getStrIngredient16()));
        }
        if (meal.getStrIngredient17() != null && !meal.getStrIngredient17().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient17() + "-Small.png",
                    meal.getStrMeasure17(), meal.getStrIngredient17()));
        }
        if (meal.getStrIngredient18() != null && !meal.getStrIngredient18().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient18() + "-Small.png",
                    meal.getStrMeasure18(), meal.getStrIngredient18()));
        }
        if (meal.getStrIngredient19() != null && !meal.getStrIngredient19().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient19() + "-Small.png",
                    meal.getStrMeasure19(), meal.getStrIngredient19()));
        }
        if (meal.getStrIngredient20() != null && !meal.getStrIngredient20().isEmpty()) {
            ingredients.add(new IngredientDTO("https://www.themealdb.com/images/ingredients/" + meal.getStrIngredient20() + "-Small.png",
                    meal.getStrMeasure20(), meal.getStrIngredient20()));
        }

        return ingredients;
    }
}
