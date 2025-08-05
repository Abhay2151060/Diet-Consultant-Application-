package com.example.dietify_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DietPreferenceActivity extends AppCompatActivity {

    Button backToProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_preference);

        backToProfileBtn = findViewById(R.id.backToProfileBtn);
        backToProfileBtn.setOnClickListener(v -> {
            // Finish current activity and return to fragment
            finish();
        });

        // Later: fetch data from database and setText for:
        // allergiesText, medicalText, dietTypeText
    }
}
