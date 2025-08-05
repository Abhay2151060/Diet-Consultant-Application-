package com.example.dietify_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView nameText, emailText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        nameText = view.findViewById(R.id.userName);
        emailText = view.findViewById(R.id.userEmail);

        // Get email from shared preferences (assuming it's stored during login)
        SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE);
        String userEmail = prefs.getString("email", null);

        if (userEmail != null) {
            fetchUserInfo(userEmail);
        } else {
            Toast.makeText(getContext(), "No user email found", Toast.LENGTH_SHORT).show();
        }

        // Set up click listeners
        view.findViewById(R.id.personalInfoLayout).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class)));

        view.findViewById(R.id.dietDetailsLayout).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), DietDetailsActivity.class)));

        view.findViewById(R.id.dietPreferenceLayout).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), DietPreferenceActivity.class)));

        view.findViewById(R.id.changePasswordLayout).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class)));

        view.findViewById(R.id.logoutLayout).setOnClickListener(v -> showLogoutConfirmationDialog());

        return view;
    }

    private void fetchUserInfo(String email) {
        String url = "http://192.168.1.27/Dietify_api/get_user_info.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("success")) {
                            String fullName = jsonObject.getString("full_name");
                            String emailAddress = jsonObject.getString("email");

                            nameText.setText(fullName);
                            emailText.setText(emailAddress);
                        } else {
                            Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "JSON error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Clear SharedPreferences
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                    Intent logoutIntent = new Intent(getActivity(), LoginActivity.class);
                    logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logoutIntent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
