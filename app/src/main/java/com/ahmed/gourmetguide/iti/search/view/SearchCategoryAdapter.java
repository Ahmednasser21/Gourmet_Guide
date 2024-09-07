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
import com.ahmed.gourmetguide.iti.model.remote.CategoryDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViweHolder> {
    List<CategoryDTO> categories;
    Context context;

    public SearchCategoryAdapter(Context context, List<CategoryDTO> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.public_row_for_search,parent,false);
        return new ViweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViweHolder holder, int position) {

        CategoryDTO category =  categories.get(position);

        holder.categoryName.setText(category.getStrCategory());

        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v->{
            NavDirections action = SearchFragmentDirections.actionSearchFragmentToCategoryMeals(category.getStrCategory());
            Navigation.findNavController(v).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    void UpdatedList(List<CategoryDTO> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    class ViweHolder extends RecyclerView.ViewHolder{

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

