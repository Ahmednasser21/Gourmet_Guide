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
import com.ahmed.gourmetguide.iti.model.CategoryDTO;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    List<CategoryDTO> categories;
    Context context;
    public CategoriesAdapter(Context context,List<CategoryDTO> categories){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       CategoryDTO category =  categories.get(position);

       holder.categoryName.setText(category.getStrCategory());

        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ConstraintLayout row;
        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(View itemView){
            super(itemView);
            layout = itemView;
            row = itemView.findViewById(R.id.cons_category_row);
            categoryName = itemView.findViewById(R.id.tv_row_category_name);
            categoryImage = itemView.findViewById(R.id.img_row_category);

        }

    }
}
