package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginScreen extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton, signupButton;
    private TextInputLayout usernameTextInputLayout, passwordTextInputLayout;
    private TextView forgetPasswordTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Initialize views
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        forgetPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate username and password fields
                boolean isValid = true;

                if (username.isEmpty()) {
                    // Set an error in the username TextInputLayout
                    usernameTextInputLayout.setError("Username is required");
                    isValid = false;
                } else {
                    // Clear the error if the field is not empty
                    usernameTextInputLayout.setError(null);
                }

                if (password.isEmpty()) {
                    // Set an error in the password TextInputLayout
                    passwordTextInputLayout.setError("Password is required");
                    isValid = false;
                } else {
                    // Clear the error if the field is not empty
                    passwordTextInputLayout.setError(null);
                }

                // If both fields are not empty, proceed to the HomeScreen
                if (isValid) {
                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignupActivity
                startActivity(new Intent(LoginScreen.this, SignupScreen.class));
            }
        });

        forgetPasswordTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //forgett pass Logic
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Exit the application when the back button is pressed on the login screen
        finishAffinity(); // Finish this activity and all parent activities
    }
}