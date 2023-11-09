package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentTransaction;

public class HomeScreen extends AppCompatActivity {

    private Button showButton;
    private FrameLayout fragmentContainer;
    private Order blueBoxFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        showButton = findViewById(R.id.showButton);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        blueBoxFragment = new Order();

        // Set an OnClickListener for the "Show Box" button
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the blue box fragment is already added
                if (blueBoxFragment.isAdded()) {
                    // Remove the blue box fragment
                    getSupportFragmentManager().beginTransaction()
                            .remove(blueBoxFragment)
                            .commit();
                } else {
                    // Add the blue box fragment
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainer, blueBoxFragment)
                            .commit();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Check if the blue box fragment is currently added
        if (blueBoxFragment.isAdded()) {
            // Remove the blue box fragment
            getSupportFragmentManager().beginTransaction()
                    .remove(blueBoxFragment)
                    .commit();
        } else {
            super.onBackPressed(); // Perform default back press action
        }
    }
}