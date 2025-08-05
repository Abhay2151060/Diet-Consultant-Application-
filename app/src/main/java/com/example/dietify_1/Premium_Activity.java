package com.example.dietify_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Premium_Activity extends AppCompatActivity {

    public TextView back_to_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        Button getPremiumButton = findViewById(R.id.getPremiumButton);
        back_to_home = findViewById(R.id.back_to_home);

        getPremiumButton.setOnClickListener(view -> {
            Toast.makeText(Premium_Activity.this, "Subscribed to Premium!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Premium_Activity.this, PaymentActivity.class);
            startActivity(intent);
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Premium_Activity.this, MainActivity.class);
                intent.putExtra("fragmentToLoad", "home");
                startActivity(intent);
                finish();
            }
        });
    }
}
