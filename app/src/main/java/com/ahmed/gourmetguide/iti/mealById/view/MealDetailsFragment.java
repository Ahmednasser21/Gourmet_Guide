package com.ahmed.gourmetguide.iti.mealById.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.mealById.presenter.MealByIdPresenter;
import com.ahmed.gourmetguide.iti.model.local.LocalMealDTO;
import com.ahmed.gourmetguide.iti.model.remote.MealDTO;
import com.ahmed.gourmetguide.iti.model.local.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.bumptech.glide.Glide;

import java.util.Calendar;

public class MealDetailsFragment extends Fragment implements OnMealView {

    MealByIdPresenter mealByIdPresenter;
    String mealId;
    MealDetailsAdapter mealDetailsAdapter;
    ImageView mealImage, addFavourite, addToCalender;
    TextView mealName, mealCountry, mealInstructions;
    RecyclerView mealIngredients;
    WebView webView;
    String videoURL;
    MealDTO tempMeal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        mealByIdPresenter = new MealByIdPresenter(this, Repository.getInstance(getContext()));
        mealByIdPresenter.getMealById(mealId);

        mealImage = view.findViewById(R.id.meal_image);
        mealName = view.findViewById(R.id.meal_name);
        mealCountry = view.findViewById(R.id.country);
        mealInstructions = view.findViewById(R.id.tv_meal_instruction);
        mealIngredients = view.findViewById(R.id.rec_meal_recycler);
        addFavourite = view.findViewById(R.id.add_favourite);
        webView = view.findViewById(R.id.webView);
        addToCalender = view.findViewById(R.id.add_to_calender);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        boolean isGuest = sharedPreferences.getBoolean(getString(R.string.preferences_is_guest), false);

        addFavourite.setOnClickListener(v -> {
            if (!isGuest) {
                LocalMealDTO meal = convertMealDTOTOLocalMealDTO(tempMeal);
                mealByIdPresenter.insertIntoFavourite(meal);
                if (isOnline()) {
                    mealByIdPresenter.uploadFav(meal);
                    Toast.makeText(getContext(), "Successfully added to favourite ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Successfully added to favourite locally", Toast.LENGTH_SHORT).show();
                }
            } else {
                showSignInDialog();
            }
        });
        addToCalender.setOnClickListener(v -> {
            if (!isGuest) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                PlanDTO plan = new PlanDTO(dayOfMonth, monthOfYear, year);
                                plan = convertMealDtoToPlanDto(plan, tempMeal);
                                mealByIdPresenter.insertIntoPlans(plan);
                                if (isOnline()) {
                                    mealByIdPresenter.uploadPlan(plan);
                                    Toast.makeText(getContext(), "Successfully added  to planes", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Successfully added  to planes locally", Toast.LENGTH_SHORT).show();
                                }


                            }
                        },
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            } else {
                showSignInDialog();
            }
        });


    }

    @Override
    public void onMealByIdSuccess(MealDTO meal) {
        tempMeal = meal;
        mealDetailsAdapter = new MealDetailsAdapter(getContext(), meal);
        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .into(mealImage);
        mealName.setText(meal.getStrMeal());
        mealCountry.setText(meal.getStrArea());
        mealInstructions.setText(meal.getStrInstructions());
        mealIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mealIngredients.setAdapter(mealDetailsAdapter);

        videoURL = meal.getStrYoutube();
        if (videoURL != null && !videoURL.isEmpty()) {
            String videoID = videoURL.split("v=")[1];
            String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoID + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadData(video, "text/html", "utf-8");
        } else {
            Log.e("VideoError", "Video URL is null or empty");
        }

    }

    @Override
    public void onMealByIdFailure(String msg) {
        Toast.makeText(getContext(), "Failed to get meal Details", Toast.LENGTH_LONG).show();
    }

    public PlanDTO convertMealDtoToPlanDto(PlanDTO planDto, MealDTO mealDto) {
        planDto.setIdMeal(mealDto.getIdMeal());
        planDto.setStrMealThumb(mealDto.getStrMealThumb());
        planDto.setStrMeal(mealDto.getStrMeal());
        return planDto;
    }

    public LocalMealDTO convertMealDTOTOLocalMealDTO(MealDTO meal) {
        LocalMealDTO localMeal = new LocalMealDTO();
        localMeal.setIdMeal(meal.getIdMeal());
        localMeal.setStrMeal(meal.getStrMeal());
        localMeal.setStrMealThumb(meal.getStrMealThumb());
        return localMeal;
    }

    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sign In Required")
                .setMessage("You need to sign in to continue. Would you like to sign in now?")
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getContext(), SignUpActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = ContextCompat.getSystemService(requireContext(), ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}