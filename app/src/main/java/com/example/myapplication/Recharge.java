package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Recharge extends AppCompatActivity implements PaymentResultListener {

    private Button recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button recharge = findViewById(R.id.buttonRecharge);
        EditText amount = findViewById(R.id.Amount);
        setSupportActionBar(toolbar);

        // Get the Intent that started this activity
        Intent intent = getIntent();
        // Getting the data passed with the Intent
        String availableBalance = intent.getStringExtra("AVAILABLE_BALANCE");
        // Finding the TextView by its id
        TextView availableBalanceText = findViewById(R.id.balance);
        // Setting the text of the TextView to the available balance
        availableBalanceText.setText(availableBalance);

        // Enable the back arrow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set click listener for the back arrow
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // This will mimic the behavior of the back button
            }
        });

        //razorpay payment gateway
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting
                // amount that is entered by user.
                String samount = amount.getText().toString();

                // rounding off the amount.
                int amount = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_test_6kAenGRDptd5U9");

                // set image
                checkout.setImage(R.drawable.order_cup_events_24);

                // initialize json object
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", "Infobyte");

                    // put description
                    object.put("description", "Test payment");

                    // to set theme color
                    object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    object.put("amount", amount);

                    // put mobile number
                   // object.put("prefill.contact", "9284064503");

                    // put email
                    //object.put("prefill.email", "chaitanyamunje@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(Recharge.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {

        // this method is called on payment success.
        // Retrieve the current balance from the TextView
        TextView availableBalanceText = findViewById(R.id.balance);
        String currentBalanceStr = availableBalanceText.getText().toString();
        double currentBalance = Double.parseDouble(currentBalanceStr);

        // Retrieve the amount recharged
        EditText amountEditText = findViewById(R.id.Amount);
        String rechargeAmountStr = amountEditText.getText().toString();
        double rechargeAmount = Double.parseDouble(rechargeAmountStr);

        // Calculate new balance
        double newBalance = currentBalance + rechargeAmount;

        // Save the new balance in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("balance", (float) newBalance);
        editor.apply();

        // Display a success message
        Toast.makeText(this, "Payment successful. New balance: " + newBalance, Toast.LENGTH_SHORT).show();

        // Set result and finish to notify MainActivity about the updated balance
        setResult(RESULT_OK);
        // Finish the activity
        finish();


    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
        // Navigate back to the Recharge activity
        Intent intent = new Intent(this, Recharge.class);
        startActivity(intent);
        finish();
    }

}