package com.ahmed.gourmetguide.iti.calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    List<PlanDTO> plans;
    Context context;
    OnDeletePlanListener onDeletePlanListener;

    public PlanAdapter(Context context, List<PlanDTO> plans, OnDeletePlanListener onDeletePlanListener) {
        this.plans = plans;
        this.context = context;
        this.onDeletePlanListener = onDeletePlanListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanDTO meal = plans.get(position);

        holder.categoryName.setText(meal.getStrMeal());
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .into(holder.categoryImage);
        holder.row.setOnClickListener(v -> {
            NavDirections action = CalenderFragmentDirections.actionCalenderFragmentToMealDetails(meal.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });
        holder.deleteFav.setOnClickListener(v -> {
            onDeletePlanListener.onDeleteListener(meal);
        });
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        String currentMonth = currentDate.format(monthFormatter);

        holder.day.setText(currentMonth+" "+meal.getDay()+" ");
    }


    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void updateData(List<PlanDTO> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        CardView row;
        TextView categoryName;
        ImageView categoryImage;
        ImageButton deleteFav;
        TextView day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            row = itemView.findViewById(R.id.cardView_plan_rwo);
            categoryName = itemView.findViewById(R.id.tv_card_meal_name_row_plan);
            categoryImage = itemView.findViewById(R.id.img_card_image_plan_row);
            deleteFav = itemView.findViewById(R.id.delete_plan);
            day = itemView.findViewById(R.id.tv_card_meal_day_row_plan);
        }

    }
}
