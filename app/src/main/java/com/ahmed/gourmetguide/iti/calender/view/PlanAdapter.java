package com.ahmed.gourmetguide.iti.calender.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.home.view.HomeActivity;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
            showDeleteAlertDialog(meal);
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, meal.getDay());
        calendar.set(Calendar.MONTH, meal.getMonth());
        calendar.set(Calendar.YEAR, meal.getYear());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());

        holder.day.setText(formattedDate);
    }


    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void updateData(List<PlanDTO> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }
    private void showDeleteAlertDialog(PlanDTO meal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Deleting Item")
                .setMessage("Are you sure? Do you want delete this item?")
                .setPositiveButton("Delete", (dialog,which)->{
                    onDeletePlanListener.onDeleteListener(meal);
                })
                .setNegativeButton("Cancel",( dialog, which)-> {
                        dialog.dismiss();
                })
                .create()
                .show();
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
