package com.example.dietify_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Details_2_Fragment extends Fragment {

    private EditText professionInput, scheduleInput, allergiesInput, medicalInput;
    private RadioGroup dietGroup;
    private int userId = 1;  // Assuming the userId is passed dynamically

    // Data passed from Details_1_Fragment
    private String age, dob, height, weight, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_2, container, false);

        // Bind views
        professionInput = view.findViewById(R.id.profession_input);
        scheduleInput = view.findViewById(R.id.schedule_input);
        allergiesInput = view.findViewById(R.id.allergies_input);
        medicalInput = view.findViewById(R.id.medical_input);
        dietGroup = view.findViewById(R.id.diet_group);
        Button submitButton = view.findViewById(R.id.complete_button);

        // Retrieve data from Details_1_Fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            age = bundle.getString("age");
            dob = bundle.getString("dob");
            height = bundle.getString("height");
            weight = bundle.getString("weight");
            gender = bundle.getString("gender");
        }

        // Submit button click handler
        submitButton.setOnClickListener(v -> {
            String profession = professionInput.getText().toString().trim();
            String schedule = scheduleInput.getText().toString().trim();
            String allergies = allergiesInput.getText().toString().trim();
            String medical = medicalInput.getText().toString().trim();

            int selectedDietId = dietGroup.getCheckedRadioButtonId();
            String dietPreference = "";

            if (selectedDietId != -1) {
                RadioButton selectedDiet = view.findViewById(selectedDietId);
                dietPreference = selectedDiet.getText().toString();
            }

            // Validate required fields
            if (profession.isEmpty() || schedule.isEmpty() || dietPreference.isEmpty() || allergies.isEmpty() || medical.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            submitDynamicDetails(profession, schedule, allergies, medical, dietPreference);
        });

        return view;
    }

    private void submitDynamicDetails(String profession, String schedule, String allergies, String medical, String dietPreference) {
        // Prepare data for server submission
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.27/Dietify_api/submit_details.php",
                response -> {
                    // Handle success response
                    Toast.makeText(getActivity(), "Dynamic details submitted successfully!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error response
                    Toast.makeText(getActivity(), "Error submitting details", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId)); // Add userId dynamically (from login session)
                params.put("profession", profession);
                params.put("schedule", schedule);
                params.put("allergies", allergies);
                params.put("medical_issues", medical);
                params.put("diet_preference", dietPreference);
                return params;
            }
        };

        // Add the request to the Volley queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }
}
