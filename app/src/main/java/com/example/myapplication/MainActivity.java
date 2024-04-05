package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        SharedPreferences sharedpref = getSharedPreferences("MYAPP", MODE_PRIVATE);
        editor = sharedpref.edit();
        new Handler().postDelayed(() -> {
            try {
                String ifloggedin = sharedpref.getString("isloggedin", "");
                if ("true".equals(ifloggedin)) {
                    Intent intu = new Intent(MainActivity.this, HomeScreen.class);
                    startActivity(intu);
                    finish();
                } else {
                    Intent intu = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intu);
                    finish();
                }
            } catch (Exception e) {
                Intent intu = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intu);
                finish();
            }
            finish();
        }, 100);
    }
}
