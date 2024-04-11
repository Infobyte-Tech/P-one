package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Createnewaccount extends AppCompatActivity {

    private EditText nameEditText, emailEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout nameTextInputLayout, emailTextInputLayout, phoneTextInputLayout, passwordTextInputLayout, confirmPasswordTextInputLayout;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewaccount);

        // Initialize EditText fields
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailidEditText);
        phoneEditText = findViewById(R.id.phEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmpasswordEditText);

        // Initialize TextInputLayout fields
        nameTextInputLayout = findViewById(R.id.NameTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailidTextInputLayout);
        phoneTextInputLayout = findViewById(R.id.phTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmpasswordTextInputLayout);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });
    }

    private void validateInput() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameTextInputLayout.setError("Name is required");
        } else {
            nameTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailTextInputLayout.setError("Email id is required");
        } else {
            emailTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            phoneTextInputLayout.setError("Phone number is required");
        } else {
            phoneTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordTextInputLayout.setError("Password is required");
        } else {
            passwordTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordTextInputLayout.setError("Confirm Password is required");
        } else {
            confirmPasswordTextInputLayout.setError(null);
        }
        if (!password.isEmpty() & !confirmPassword.isEmpty()){
            if (!password.equals(confirmPassword)) {
                passwordTextInputLayout.setError("Passwords do not match");
                confirmPasswordTextInputLayout.setError("Passwords do not match");
            } else {
                passwordTextInputLayout.setError(null);
                confirmPasswordTextInputLayout.setError(null);
            }
        }
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) &&
                !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword) &&
                password.equals(confirmPassword)) {
            Intent startEmailauth = new Intent(Createnewaccount.this , phonenumberverification.class);
            startEmailauth.putExtra("email" , email);
            startEmailauth.putExtra("phonenumber" , phone);
            startEmailauth.putExtra("name" , name);
            startEmailauth.putExtra("password" , password);
            startEmailauth.putExtra("Balance", 0);
            startActivity(startEmailauth);
        }
    }
}
