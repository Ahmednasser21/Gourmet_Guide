package com.ahmed.gourmetguide.iti.meal_details_by_id.meal_view;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.meal_details_by_id.meal_presenter.MealByIdPresenter;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.bumptech.glide.Glide;

public class MealDetails extends Fragment implements OnMealView {

    MealByIdPresenter mealByIdPresenter;
    String mealId;
    MealDetailsAdapter mealDetailsAdapter;
    ImageView mealImage;
    TextView mealName, mealCountry, mealInstructions;
    RecyclerView mealIngredients;
    WebView webView;
    String videoURL;

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
        webView = view.findViewById(R.id.webView);


    }

    @Override
    public void onMealByIdSuccess(MealDTO meal) {

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
}