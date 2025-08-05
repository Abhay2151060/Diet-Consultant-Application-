package com.example.dietify_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class reset_pass extends AppCompatActivity {

    private EditText emailInput;
    public Button resetButton;
    public TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass); // Ensure the correct XML filename

        // Initialize Views
        emailInput = findViewById(R.id.email_input);
        resetButton = findViewById(R.id.reset_button);
        backToLogin = findViewById(R.id.back_to_login);

        // Handle Reset Button Click
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(reset_pass.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    // Here you can implement actual password reset logic (e.g., Firebase Auth)
                    Toast.makeText(reset_pass.this, "Reset link sent to: " + email, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle Back to Login Click
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reset_pass.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
