package com.example.dietify_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us); // Ensure your XML file is named 'activity_about_us.xml'

        // Initialize Views
        ImageView logo = findViewById(R.id.logo);
        TextView appName = findViewById(R.id.app_name);
        TextView aboutUs = findViewById(R.id.about_us);
        TextView description = findViewById(R.id.description);
        Button nextButton = findViewById(R.id.next_button);

        // Set OnClickListener for Next Button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this, Acc_holder.class);
                startActivity(intent);
            }
        });
    }
}
