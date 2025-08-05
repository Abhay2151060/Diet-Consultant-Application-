package com.example.dietify_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DietPlanFragment extends Fragment {

    private ImageView dietImage;
    private TextView planName, planDescription;
    private LinearLayout mealContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_plan, container, false);

        // Initialize views
        dietImage = view.findViewById(R.id.diet_image);
        planName = view.findViewById(R.id.plan_name);
        planDescription = view.findViewById(R.id.plan_description);
        mealContainer = view.findViewById(R.id.meal_container);

        return view;
    }


    public void updateDietPlan(String name, String description, int imageResId) {
        if (planName != null) planName.setText(name);
        if (planDescription != null) planDescription.setText(description);
        if (dietImage != null) dietImage.setImageResource(imageResId);
    }

    public static class Meal {
        private final String planName;
        private final String planDescription;
        private final String[] mealDetails; // [time1, items1, time2, items2...]

        public Meal(String planName, String planDescription, String[] mealDetails) {
            this.planName = planName;
            this.planDescription = planDescription;
            this.mealDetails = mealDetails;
        }
    }

    public void addMealCard(Meal meal) {
        if (getContext() == null || mealContainer == null) return;

        View mealCard = LayoutInflater.from(getContext()).inflate(R.layout.meal_card, mealContainer, false);
        mealContainer.addView(mealCard);

        // The meal_card.xml already contains all the emoji content statically
        // We're just using the fragment to organize multiple meal cards
    }
}