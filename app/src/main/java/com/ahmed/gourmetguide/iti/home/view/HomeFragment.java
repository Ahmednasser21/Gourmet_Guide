package com.ahmed.gourmetguide.iti.home.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.gourmetguide.iti.R;
import com.ahmed.gourmetguide.iti.home.presenter.HomePresenter;
import com.ahmed.gourmetguide.iti.model.remote.CategoryDTO;
import com.ahmed.gourmetguide.iti.model.remote.MealDTO;
import com.ahmed.gourmetguide.iti.repo.Repository;
import com.ahmed.gourmetguide.iti.signup.view.SignUpActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
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
    View rootView;
    private static final String TAG = "HomeFragment";
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
        rootView = view;
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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(getString(R.string.preference_login_file_key), Context.MODE_PRIVATE);
        boolean isGuest = sharedPreferences.getBoolean(getString(R.string.preferences_is_guest), false);
        profileImage.setOnClickListener(v -> {
            if (!isGuest) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToProfileFragment();
                Navigation.findNavController(v).navigate(action);
            }else{
                showSignInDialog();
            }
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
        randomMeal = new MealDTO();
        Log.i(TAG, "onRandomMealFailure: ");
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
        Log.i(TAG, "onCategoryFailure: ");
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!isNetworkAvailable() && rootView!=null) {
            showNoInternetSnackbar(rootView);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextCompat.getSystemService(requireContext(),ConnectivityManager.class);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void showNoInternetSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(view, "No internet connection", Snackbar.LENGTH_SHORT)
                .setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                });
        snackbar.show();
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
}