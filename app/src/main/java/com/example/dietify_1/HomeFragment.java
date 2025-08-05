package com.example.dietify_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private TextView userName;
    private static final String USER_URL = "http://192.168.1.27/Dietify_api/get_user.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        userName = view.findViewById(R.id.userName);
        Button upgradeBtn = view.findViewById(R.id.upgradeBtn);
        RelativeLayout dietBox = view.findViewById(R.id.dietBox);
        RelativeLayout detailsBox = view.findViewById(R.id.detailsBox);

        loadUserFullName();

        upgradeBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Premium_Activity.class));
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        dietBox.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).bottomNavigation.setSelectedItemId(R.id.nav_dietplan);
            }
        });

        detailsBox.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DietDetailsActivity.class));
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        return view;
    }

    private void loadUserFullName() {
        SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email == null) {
            userName.setText("(User not logged in)");
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, USER_URL,
                response -> {
                    if (response.equals("not_found")) {
                        userName.setText("(User not found)");
                    } else {
                        userName.setText(response);
                    }
                },
                error -> Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(request);
    }
}
