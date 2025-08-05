package com.example.dietify_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Acc_holder extends AppCompatActivity {

    public RelativeLayout user1, user2, addUser;
    public Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acc_holder); // Ensure this matches your XML filename

        // Initialize Views
        user1 = findViewById(R.id.user_1);
        user2 = findViewById(R.id.user_2);
        addUser = findViewById(R.id.add_user);
        nextButton = findViewById(R.id.next_button);

        // Handle  Selection
        user1.setOnClickListener(v -> showToast("Member 1 Selected"));

        user2.setOnClickListener(v -> showToast("Member 2 Selected"));

        addUser.setOnClickListener(v -> showToast("Member 3 Selected"));

        // Handle Next Button Click
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Acc_holder.this, MainActivity.class); // Replace with your next activity
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(Acc_holder.this, message, Toast.LENGTH_SHORT).show();
    }
}
