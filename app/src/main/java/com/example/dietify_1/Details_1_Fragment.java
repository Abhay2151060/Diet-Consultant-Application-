package com.example.dietify_1;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

public class Details_1_Fragment extends Fragment {

    private EditText ageInput, dobInput, heightInput, weightInput;
    private RadioGroup genderGroup;
    private Calendar calendar;

    // Assuming userId is already available after login
    private int userId = 1;  // For now, set it manually, replace with actual userId after login

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_1, container, false);

        // Initialize views
        ageInput = view.findViewById(R.id.age_input);
        dobInput = view.findViewById(R.id.dob_input);
        heightInput = view.findViewById(R.id.height_input);
        weightInput = view.findViewById(R.id.weight_input);
        genderGroup = view.findViewById(R.id.gender_group);
        Button continueButton = view.findViewById(R.id.continue_button);
        calendar = Calendar.getInstance();

        // Set up date picker for DOB field
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDobField();
        };

        dobInput.setOnClickListener(v -> new DatePickerDialog(requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());

        // Continue button click handler
        continueButton.setOnClickListener(v -> {
            if (validateInputs()) {
                submitDetails();
            }
        });

        return view;
    }

    private void updateDobField() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        dobInput.setText(sdf.format(calendar.getTime()));
    }

    private boolean validateInputs() {
        if (dobInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please select your date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ageInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter your age", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (heightInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter your height", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (weightInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Please enter your weight", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (genderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(requireContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void submitDetails() {
        String age = ageInput.getText().toString().trim();
        String dob = dobInput.getText().toString().trim();
        String height = heightInput.getText().toString().trim();
        String weight = weightInput.getText().toString().trim();

        RadioButton selectedGender = genderGroup.findViewById(genderGroup.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        // Prepare data for server submission
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.27/Dietify_api/submit_details.php",
                response -> {
                    // Navigate to the next fragment (Details_2_Fragment)
                    navigateToDetails2(age, dob, height, weight, gender);
                },
                error -> {
                    // Handle error response
                    Toast.makeText(getActivity(), "Error submitting details", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId)); // Add userId dynamically (from login session)
                params.put("age", age);
                params.put("dob", dob);
                params.put("gender", gender);
                params.put("height", height);
                params.put("weight", weight);
                return params;
            }
        };

        // Add the request to the Volley queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void navigateToDetails2(String age, String dob, String height, String weight, String gender) {
        // Move to the next fragment (Details_2_Fragment)
        Details_2_Fragment details2Fragment = new Details_2_Fragment();

        // Pass data to the next fragment using Bundle
        Bundle bundle = new Bundle();
        bundle.putString("age", age);
        bundle.putString("dob", dob);
        bundle.putString("height", height);
        bundle.putString("weight", weight);
        bundle.putString("gender", gender);
        details2Fragment.setArguments(bundle);

        // Replace the current fragment with Details_2_Fragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, details2Fragment)
                .addToBackStack(null)
                .commit();
    }
}
