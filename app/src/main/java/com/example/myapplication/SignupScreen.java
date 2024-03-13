package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class SignupScreen extends AppCompatActivity {

    private Button loginButton, signUpButton;
    private TextInputEditText nameEditText, mobileEditText, emailEditText;
    private TextInputLayout nameTextInputLayout, mobileTextInputLayout, emailTextInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        // Initialize views
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signupButton);
        nameEditText = findViewById(R.id.nameEditText);
        mobileEditText = findViewById(R.id.mobilenumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        mobileTextInputLayout = findViewById(R.id.mobileNumberFieldLayout);
        emailTextInputLayout = findViewById(R.id.emailFieldLayout);

        // Set click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to LoginActivity
                startActivity(new Intent(SignupScreen.this, LoginScreen.class));
                finish(); // Finish this activity
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input data
                String name = nameEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                // Validate and process the user input data here
                boolean isValid = true;

                if (name.isEmpty()) {
                    nameTextInputLayout.setError("Enter your name");
                    isValid = false;
                } else {
                    nameTextInputLayout.setError(null);
                }

                if (mobile.isEmpty()) {
                    mobileTextInputLayout.setError("Enter your mobile number");
                    isValid = false;
                } else if (mobile.length() != 10) {
                    mobileTextInputLayout.setError("Not Valid Mobile number!");
                    isValid = false;
                } else {
                    mobileTextInputLayout.setError(null);
                }

                if (email.isEmpty()) {
                    emailTextInputLayout.setError("Enter your email");
                    isValid = false;
                } else if (!isValidEmail(email)) {
                    emailTextInputLayout.setError("Enter a valid email address");
                    isValid = false;
                } else {
                    emailTextInputLayout.setError(null);
                }

                if (isValid) {
                    // For demo, show the captured input in a toast
                    String userData = "Name: " + name + "\nMobile: " + mobile + "\nEmail: " + email;
                    Toast.makeText(SignupScreen.this, userData, Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    // Override onBackPressed to navigate back to LoginActivity on back press
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupScreen.this, LoginScreen.class));
        finish(); // Finish this activity
    }
    // Email validation method using regex
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }
}
