package com.example.myapplication;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseApp;

import java.util.HashSet;
import java.util.Set;

public class LoginScreen extends AppCompatActivity {

    private EditText passwordEditText;
    private ImageButton showHideButton;

    private Integer inputtype;

    private TextView createnewaccount;
    private boolean isPasswordVisible = false;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        passwordEditText = findViewById(R.id.passwordEditText);
        showHideButton = findViewById(R.id.imageButton2);
        createnewaccount = findViewById(R.id.CreateNewAccount);
        showHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                updatePasswordVisibility();
            }
        });
        createnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this , Createnewaccount.class);
                startActivity(intent);
            }
        });

        FirebaseApp.initializeApp(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        ProgressBar progbut = findViewById(R.id.progressbar);
        EditText emailadr = findViewById(R.id.usernameEditText);
        Button loginbut = findViewById(R.id.loginButton);
        TextInputLayout username = findViewById(R.id.usernameTextInputLayout);
        TextInputLayout passwordInputLayout = findViewById(R.id.passwordTextInputLayout);
        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbut.setText("");
                progbut.setVisibility(View.VISIBLE);

                String email = emailadr.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    progbut.setVisibility(View.GONE);
                    loginbut.setText("Login");
                    if (email.equals("")) {
                        username.setError("Email id cannot be empty!");
                    } else {
                        passwordInputLayout.setError("Password cannot be empty!");
                    }
                } else {
                    username.setError(null);
                    passwordInputLayout.setError(null);
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginScreen.this, task -> {
                                if (task.isSuccessful()) {
                                    SharedPreferences sharedpref = getSharedPreferences("MYAPP", MODE_PRIVATE);
                                    editor = sharedpref.edit();
                                    editor.putString("isloggedin", "true");
                                    editor.apply();
                                    Intent intentti = new Intent(LoginScreen.this, HomeScreen.class);
                                    startActivity(intentti);
                                    finish();
                                } else {
                                    progbut.setVisibility(View.GONE);
                                    loginbut.setText("Login");
                                    Toast.makeText(LoginScreen.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private void updatePasswordVisibility() {
        if(isPasswordVisible){
            showHideButton.setBackgroundResource(R.drawable.ic_eye_outline_24);
            inputtype = InputType.TYPE_CLASS_TEXT;
        }
        else{
            showHideButton.setBackgroundResource(R.drawable.ic_eye_off_outline_24);
            inputtype = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        passwordEditText.setInputType(inputtype);
        // Move cursor to the end of the text
        passwordEditText.setSelection(passwordEditText.getText().length());
    }
}