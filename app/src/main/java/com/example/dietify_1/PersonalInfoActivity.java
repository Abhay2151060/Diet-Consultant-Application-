package com.example.dietify_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonalInfoActivity extends AppCompatActivity {

    TextView nameTextView, emailTextView, dobTextView, professionTextView, scheduleTextView;
    Button backToProfileBtn;

    private static final String USER_INFO_URL = "http://192.168.1.27/Dietify_api/get_full_user_info.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        // Link XML views
        nameTextView = findViewById(R.id.personalName);
        emailTextView = findViewById(R.id.personalEmail);
        dobTextView = findViewById(R.id.personalDob);
        professionTextView = findViewById(R.id.personalProfession);
        scheduleTextView = findViewById(R.id.personalSchedule);
        backToProfileBtn = findViewById(R.id.backToProfileBtn);

        // Back button
        backToProfileBtn.setOnClickListener(v -> finish());

        // Get user_id from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId != -1) {
            fetchUserData(userId); // Fetch data using user ID
        } else {
            Toast.makeText(this, "User not found in preferences", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserData(int userId) {
        StringRequest request = new StringRequest(Request.Method.POST, USER_INFO_URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.has("error")) {
                            Toast.makeText(this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                        } else {
                            nameTextView.setText("Name: " + jsonObject.getString("full_name"));
                            emailTextView.setText("Email: " + jsonObject.getString("email"));
                            dobTextView.setText("DOB: " + jsonObject.getString("dob"));
                            professionTextView.setText("Profession: " + jsonObject.getString("profession"));
                            scheduleTextView.setText("Daily Schedule: " + jsonObject.getString("schedule"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Network error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", String.valueOf(userId)); // Send the user_id to the server
                return data;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
