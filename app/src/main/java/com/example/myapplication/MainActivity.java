package com.example.myapplication;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button showRedButton;
    private Button showBlueButton;
    private FrameLayout fragmentContainer;
    private red_order redBoxFragment;
    private blue_order blueBoxFragment;
    private boolean isRedFragmentOpened = false;
    private boolean isBlueFragmentOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRedButton = findViewById(R.id.showRedButton);
        showBlueButton = findViewById(R.id.showBlueButton);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        redBoxFragment = new red_order();
        blueBoxFragment = new blue_order();

        showRedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRedFragmentOpened) {
                    isRedFragmentOpened = true;
                    showRedOrderFragment();
                    disableHomeScreen();
                }
            }
        });

        showBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBlueFragmentOpened) {
                    isBlueFragmentOpened = true;
                    showBlueOrderFragment();
                    disableHomeScreen();
                }
            }
        });
    }

    private void showRedOrderFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, redBoxFragment)
                .commit();
        redBoxFragment.setOnFragmentCloseListener(new red_order.OnFragmentCloseListener() {
            @Override
            public void onClose() {
                isRedFragmentOpened = false;
                enableHomeScreen();
            }
        });
    }

    private void showBlueOrderFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, blueBoxFragment)
                .commit();
        blueBoxFragment.setOnFragmentCloseListener(new blue_order.OnFragmentCloseListener() {
            @Override
            public void onClose() {
                isBlueFragmentOpened = false;
                enableHomeScreen();
            }
        });
    }

    private void disableHomeScreen() {
        showRedButton.setEnabled(false);
        showBlueButton.setEnabled(false);
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Consume touch events
            }
        });
    }

    private void enableHomeScreen() {
        showRedButton.setEnabled(true);
        showBlueButton.setEnabled(true);
        findViewById(android.R.id.content).setOnTouchListener(null);
    }

    @Override
    public void onBackPressed() {
        if (isRedFragmentOpened) {
            red_order redFragment = (red_order) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (redFragment != null && redFragment.isVisible()) {
                getSupportFragmentManager().beginTransaction().remove(redFragment).commit();
                enableHomeScreen();
                isRedFragmentOpened = false;
                return;
            }
        }

        if (isBlueFragmentOpened) {
            blue_order blueFragment = (blue_order) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (blueFragment != null && blueFragment.isVisible()) {
                getSupportFragmentManager().beginTransaction().remove(blueFragment).commit();
                enableHomeScreen();
                isBlueFragmentOpened = false;
                return;
            }
        }

        super.onBackPressed();
    }

}
