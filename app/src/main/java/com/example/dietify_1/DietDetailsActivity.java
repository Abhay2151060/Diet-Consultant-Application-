package com.example.dietify_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DietDetailsActivity extends AppCompatActivity {

    TextView genderTextView, ageTextView, heightTextView, weightTextView;
    Button backToProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_details);

        // Link XML views
        genderTextView = findViewById(R.id.dietGender);
        ageTextView = findViewById(R.id.dietAge);
        heightTextView = findViewById(R.id.dietHeight);
        weightTextView = findViewById(R.id.dietWeight);
        backToProfileBtn = findViewById(R.id.backToProfileBtn);

        // Sample data - replace with DB values later
        genderTextView.setText("Gender: Female");
        ageTextView.setText("Age: 25");
        heightTextView.setText("Height: 165 cm");
        weightTextView.setText("Weight: 60 kg");

        // Back to profile screen
        backToProfileBtn.setOnClickListener(v -> finish());
    }
}
