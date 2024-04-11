package com.example.myapplication;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Recharge extends AppCompatActivity implements PaymentResultListener {

    private TextView availableBalanceText;
    private EditText amountEditText;
    private Button rechargeButton;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private Checkout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        availableBalanceText = findViewById(R.id.balance);
        amountEditText = findViewById(R.id.Amount);
        rechargeButton = findViewById(R.id.buttonRecharge);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String contact = dataSnapshot.child("phonenumber").getValue(String.class);
                        int availableBalance = dataSnapshot.child("balance").getValue(Integer.class);

                        availableBalanceText.setText(String.valueOf(availableBalance));

                        rechargeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String rechargeAmountStr = amountEditText.getText().toString().trim();
                                if (!rechargeAmountStr.isEmpty()) {
                                    int rechargeAmount = Integer.parseInt(rechargeAmountStr);

                                    // Initialize Razorpay
                                    checkout = new Checkout();
                                    checkout.setKeyID("rzp_test_6kAenGRDptd5U9"); // Replace with your Razorpay Key ID

                                    try {
                                        JSONObject options = new JSONObject();
                                        options.put("name", name);
                                        options.put("description", "Add Money To Wallet");
                                        options.put("currency", "INR");
                                        options.put("amount", rechargeAmount * 100); // Amount in paise
                                        options.put("contact", contact);
                                        options.put("email", email);

                                        checkout.open(Recharge.this, options);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(Recharge.this, "Please enter a valid recharge amount", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Recharge", "Failed to read user data", databaseError.toException());
                }
            });
        }

        // Handling back navigation
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String paymentId) {
        String rechargeAmountStr = amountEditText.getText().toString().trim();
        if (!rechargeAmountStr.isEmpty()) {
            int rechargeAmount = Integer.parseInt(rechargeAmountStr);

            // Update user's balance in Firebase
            userRef.child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        int currentBalance = snapshot.getValue(Integer.class);
                        int newBalance = currentBalance + rechargeAmount;

                        userRef.child("balance").setValue(newBalance)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Recharge.this, "Payment successful. New balance: " + newBalance, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Recharge.this, "Failed to update balance", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Recharge", "Failed to read balance data", error.toException());
                }
            });
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (checkout != null) {
            checkout.clearUserData(this);
        }
    }
}