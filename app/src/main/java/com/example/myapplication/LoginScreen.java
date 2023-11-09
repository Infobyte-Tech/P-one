package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginScreen extends AppCompatActivity {

    private TextInputLayout usernameTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty()) {
                    // Set an error in the username TextInputLayout
                    usernameTextInputLayout.setError("Username is required");
                    return; // Prevent further execution
                } else {
                    // Clear the error if the field is not empty
                    usernameTextInputLayout.setError(null);
                }

                if (password.isEmpty()) {
                    // Set an error in the password TextInputLayout
                    passwordTextInputLayout.setError("Password is required");
                    return; // Prevent further execution
                } else {
                    // Clear the error if the field is not empty
                    passwordTextInputLayout.setError(null);
                }

                // If both fields are not empty, proceed to the HomeScreen
                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
