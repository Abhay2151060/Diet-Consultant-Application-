package com.example.dietify_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup paymentMethodGroup;
    private LinearLayout cardDetailsLayout, netBankingLayout, upiLayout, walletLayout;
    private EditText cardName, cardNumber, cardExpiry, cardCvv, upiId;
    private Spinner spinnerBank, spinnerWallet;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_screen);

        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        cardDetailsLayout = findViewById(R.id.cardDetailsLayout);
        netBankingLayout = findViewById(R.id.netBankingLayout);
        upiLayout = findViewById(R.id.upiLayout);
        walletLayout = findViewById(R.id.walletLayout);

        cardName = findViewById(R.id.cardName);
        cardNumber = findViewById(R.id.cardNumber);
        cardExpiry = findViewById(R.id.cardExpiry);
        cardCvv = findViewById(R.id.cardCvv);
        upiId = findViewById(R.id.upiId);

        spinnerBank = findViewById(R.id.spinnerBank);
        spinnerWallet = findViewById(R.id.spinnerWallet);

        btnPay = findViewById(R.id.btnPay);

        paymentMethodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                cardDetailsLayout.setVisibility(View.GONE);
                netBankingLayout.setVisibility(View.GONE);
                upiLayout.setVisibility(View.GONE);
                walletLayout.setVisibility(View.GONE);

                if (checkedId == R.id.radioCard) {
                    cardDetailsLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioNetBanking) {
                    netBankingLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioUPI) {
                    upiLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioWallet) {
                    walletLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select your bank", "HDFC", "ICICI", "SBI", "Axis Bank"});
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(bankAdapter);

        ArrayAdapter<String> walletAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Select your wallet", "Paytm", "PhonePe", "Amazon Pay", "MobiKwik", "FreeCharge"});
        walletAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWallet.setAdapter(walletAdapter);

        btnPay.setOnClickListener(v -> validateAndProcessPayment());
    }

    private void validateAndProcessPayment() {
        int selectedPaymentMethod = paymentMethodGroup.getCheckedRadioButtonId();

        if (selectedPaymentMethod == -1) {
            showToast("Please select a payment method");
            return;
        }

        if (selectedPaymentMethod == R.id.radioCard) {
            if (!validateCardDetails()) return;
            processCardPayment();
        }
        else if (selectedPaymentMethod == R.id.radioNetBanking) {
            if (spinnerBank.getSelectedItemPosition() == 0) {
                showToast("Please select your bank");
                return;
            }
            processNetBankingPayment();
        }
        else if (selectedPaymentMethod == R.id.radioUPI) {
            if (!validateUpiId()) return;
            processUpiPayment();
        }
        else if (selectedPaymentMethod == R.id.radioWallet) {
            if (spinnerWallet.getSelectedItemPosition() == 0) {
                showToast("Please select your wallet");
                return;
            }
            processWalletPayment();
        }
    }

    private boolean validateCardDetails() {
        if (cardName.getText().toString().trim().isEmpty()) {
            showToast("Please enter cardholder name");
            return false;
        }

        String cardNum = cardNumber.getText().toString().trim();
        if (cardNum.length() != 16 || !cardNum.matches("\\d+")) {
            showToast("Please enter valid 16-digit card number");
            return false;
        }

        String expiry = cardExpiry.getText().toString().trim();
        if (expiry.length() != 5 || !expiry.contains("/")) {
            showToast("Please enter valid expiry date (MM/YY)");
            return false;
        }

        String[] parts = expiry.split("/");
        if (parts.length != 2) {
            showToast("Invalid expiry format. Use MM/YY");
            return false;
        }

        try {
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            if (month < 1 || month > 12) {
                showToast("Expiry month must be between 01 and 12");
                return false;
            }

            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int currentMonth = calendar.get(java.util.Calendar.MONTH) + 1; // Months are 0-indexed
            int currentYear = calendar.get(java.util.Calendar.YEAR) % 100; // Get last two digits (like 24 for 2024)

            if (year < currentYear || (year == currentYear && month < currentMonth)) {
                showToast("Card has expired");
                return false;
            }

        } catch (NumberFormatException e) {
            showToast("Invalid expiry date format");
            return false;
        }

        String cvv = cardCvv.getText().toString().trim();
        if (cvv.length() != 3 || !cvv.matches("\\d{3}")) {
            showToast("Please enter valid 3-digit CVV");
            return false;
        }

        return true;
    }


    private boolean validateUpiId() {
        String upi = upiId.getText().toString().trim();
        if (upi.isEmpty()) {
            showToast("Please enter UPI ID");
            return false;
        }

        if (!upi.contains("@")) {
            showToast("Please enter valid UPI ID (username@upi)");
            return false;
        }

        return true;
    }

    private void processCardPayment() {
        showToast("Processing card payment...");
    }

    private void processNetBankingPayment() {
        String bank = spinnerBank.getSelectedItem().toString();
        showToast("Processing net banking payment with " + bank);
    }

    private void processUpiPayment() {
        String upi = upiId.getText().toString().trim();
        showToast("Processing UPI payment to " + upi);
    }

    private void processWalletPayment() {
        String wallet = spinnerWallet.getSelectedItem().toString();
        showToast("Processing wallet payment with " + wallet);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}