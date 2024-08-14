package com.ahmed.gourmetguide.iti.home.home_view;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.home.home_presenter.HomePresenter;
import com.ahmed.gourmetguide.iti.model.CategoryDTO;
import com.ahmed.gourmetguide.iti.model.MealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeFragment extends Fragment implements OnRandomMealView, OnCategoryView {
    HomePresenter homePresenter;
    ImageView randomMealImg, profileImage;
    TextView randomMealName;
    CategoriesAdapter categoriesAdapter;
    RecyclerView categoriesRecycler;
    CardView randomMealCard;
    MealDTO randomMeal;
    FirebaseUser user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        randomMealImg = view.findViewById(R.id.img_card_image);
        randomMealName = view.findViewById(R.id.tv_card_daily);
        homePresenter = new HomePresenter(Repository.getInstance(getContext()), this, this);
        homePresenter.getRandomMeal();
        homePresenter.getCategories();
        profileImage = view.findViewById(R.id.profile_image_home);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Uri profilePictureUri = user.getPhotoUrl();
            if (profilePictureUri != null) {
                Glide.with(this)
                        .load(profilePictureUri)
                        .placeholder(R.drawable.profile_image_default)
                        .error(R.drawable.profile_image_default)
                        .into(profileImage);

            }
        }
        categoriesRecycler = view.findViewById(R.id.categories_rec);
        randomMealCard = view.findViewById(R.id.cardView);

        randomMealCard.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToMealDetails(randomMeal.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });

        profileImage.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections.actionHomeFragmentToProfileFragment();
            Navigation.findNavController(v).navigate(action);
        });

    }

    @Override
    public void onRandomMealSuccess(MealDTO meal) {
        randomMealName.setText(meal.getStrMeal());
        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .into(randomMealImg);
        randomMeal = meal;
    }

    @Override
    public void onRandomMealFailure(String msg) {
        Toast.makeText(getContext(), "msg random", Toast.LENGTH_LONG).show();
        randomMeal = new MealDTO();
    }

    @Override
    public void onCategorySuccess(List<CategoryDTO> categories) {
        categoriesAdapter = new CategoriesAdapter(getContext(), categories);
        categoriesRecycler.setAdapter(categoriesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoriesRecycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCategoryFailure(String errorMsg) {
        Toast.makeText(getContext(), "msg category", Toast.LENGTH_LONG).show();
    }
}