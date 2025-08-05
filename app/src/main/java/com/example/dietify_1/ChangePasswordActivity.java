package com.example.dietify_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassword, newPassword, confirmPassword;
    Button changePasswordBtn, backToProfile;

    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "userPrefs";
    private static final String PASSWORD_KEY = "password"; // store current password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        backToProfile = findViewById(R.id.backToProfile);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (!sharedPreferences.contains(PASSWORD_KEY)) {
            sharedPreferences.edit().putString(PASSWORD_KEY, "123456").apply(); // default password
        }

        changePasswordBtn.setOnClickListener(v -> {
            String oldPass = oldPassword.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();

            String savedPass = sharedPreferences.getString(PASSWORD_KEY, "");

            if (!oldPass.equals(savedPass)) {
                Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
            } else if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                sharedPreferences.edit().putString(PASSWORD_KEY, newPass).apply();
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                // Finish this activity to return to MainActivity where fragment is loaded
                finish();
            }
        });

        backToProfile.setOnClickListener(v -> {
            finish(); // just go back to the activity that holds ProfileFragment
        });
    }
}
