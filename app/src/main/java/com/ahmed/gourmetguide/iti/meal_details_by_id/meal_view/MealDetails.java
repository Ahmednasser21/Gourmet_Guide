package com.ahmed.gourmetguide.iti.meal_details_by_id.meal_view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.ahmed.gourmetguide.iti.calender.presenter.CalenderPresenter;
import com.ahmed.gourmetguide.iti.meal_details_by_id.meal_presenter.MealByIdPresenter;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.model.PlanDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.bumptech.glide.Glide;

import java.util.Calendar;

public class MealDetails extends Fragment implements OnMealView {

    MealByIdPresenter mealByIdPresenter;
    String mealId;
    MealDetailsAdapter mealDetailsAdapter;
    ImageView mealImage,addFavourite , addToCalender;
    TextView mealName, mealCountry, mealInstructions;
    RecyclerView mealIngredients;
    WebView webView;
    String videoURL;
    MealDTO tempMeal;
    CalenderPresenter calenderPresenter;

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
        mealId = MealDetailsArgs.fromBundle(getArguments()).getMealId();
        mealByIdPresenter = new MealByIdPresenter(this, Repository.getInstance(getContext()));
        mealByIdPresenter.getMealById(mealId);

        mealImage = view.findViewById(R.id.meal_image);
        mealName = view.findViewById(R.id.meal_name);
        mealCountry = view.findViewById(R.id.country);
        mealInstructions = view.findViewById(R.id.tv_meal_instruction);
        mealIngredients = view.findViewById(R.id.rec_meal_recycler);
        addFavourite = view.findViewById(R.id.add_favourite);
        webView = view.findViewById(R.id.webView);
        addToCalender= view.findViewById(R.id.add_to_calender);
        calenderPresenter = new CalenderPresenter(null,Repository.getInstance(getContext()));

        addFavourite.setOnClickListener(v->{

                mealByIdPresenter.insertIntoFavourite(tempMeal);
                Toast.makeText(getContext(),"Added successfully",Toast.LENGTH_LONG).show();
        });
        addToCalender.setOnClickListener(v->{
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
                            PlanDTO plan = new PlanDTO(dayOfMonth,monthOfYear+1,year);

                            calenderPresenter.insertIntoPlanByDAy(convertMealDtoToPlanDto(plan,tempMeal));

                        }
                    },
                    year, month, day);
            datePickerDialog.show();
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
        Toast.makeText(getContext(),"Failed to get meal Details",Toast.LENGTH_LONG).show();
    }
    public PlanDTO convertMealDtoToPlanDto(PlanDTO planDto, MealDTO mealDto){
        planDto.setIdMeal(mealDto.getIdMeal());
        planDto.setStrMealThumb(mealDto.getStrMealThumb());
        planDto.setStrCategory(mealDto.getStrCategory());
        planDto.setStrMeal(mealDto.getStrMeal());
        planDto.setStrArea(mealDto.getStrArea());
        planDto.setStrInstructions(mealDto.getStrInstructions());
        planDto.setStrTags(mealDto.getStrTags());
        planDto.setStrYoutube(mealDto.getStrYoutube());
        planDto.setStrIngredient1(mealDto.getStrIngredient1());
        planDto.setStrIngredient2(mealDto.getStrIngredient2());
        planDto.setStrIngredient3(mealDto.getStrIngredient3());
        planDto.setStrIngredient4(mealDto.getStrIngredient4());
        planDto.setStrIngredient5(mealDto.getStrIngredient5());
        planDto.setStrIngredient6(mealDto.getStrIngredient6());
        planDto.setStrIngredient7(mealDto.getStrIngredient7());
        planDto.setStrIngredient8(mealDto.getStrIngredient8());
        planDto.setStrIngredient9(mealDto.getStrIngredient9());
        planDto.setStrIngredient10(mealDto.getStrIngredient10());
        planDto.setStrIngredient11(mealDto.getStrIngredient11());
        planDto.setStrIngredient12(mealDto.getStrIngredient12());
        planDto.setStrIngredient13(mealDto.getStrIngredient13());
        planDto.setStrIngredient14(mealDto.getStrIngredient14());
        planDto.setStrIngredient15(mealDto.getStrIngredient15());
        return planDto;
    }
}