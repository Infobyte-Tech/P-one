package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class emailverification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailverification);
        TextView tv = findViewById(R.id.emailotp);
        String emailid = getIntent().getStringExtra("email");
        String newEmail = hideEmail(emailid);
        tv.setText(tv.getText() + " " + newEmail);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


    }
    private String hideEmail(String email) {
        // Split email address into username and domain
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        // Calculate the length of the portion to be replaced with asterisks
        int lengthToReplace = (int) Math.ceil(username.length() * 0.6);

        // Replace the portion of the username with asterisks
        String hiddenUsername = username.substring(0, lengthToReplace).replaceAll(".", "*")
                + username.substring(lengthToReplace);

        // Concatenate the hidden username and domain
        return hiddenUsername + "@" + domain;
    }


}