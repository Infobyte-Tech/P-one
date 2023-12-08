package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button showRedButton;
    private Button showBlueButton;
    private FrameLayout fragmentContainer;
    private red_order redBoxFragment;
    private blue_order blueBoxFragment;
    private boolean isRedFragmentOpened = false;
    private boolean isBlueFragmentOpened = false;
    private TextView countdownDisplay;
    private CountDownTimer countDownTimer;
    private ProgressBar circularProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRedButton = findViewById(R.id.showRedButton);
        showBlueButton = findViewById(R.id.showBlueButton);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        redBoxFragment = new red_order();
        blueBoxFragment = new blue_order();
        countdownDisplay = findViewById(R.id.countdownDisplay);
        circularProgressBar = findViewById(R.id.circularProgressBar);


        startCountdown();

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

    private void startCountdown() {
        countDownTimer = new CountDownTimer(180000, 1000) { // 180000 milliseconds = 3 minutes
            @Override
            public void onTick(long millisUntilFinished) {
                // Convert millisUntilFinished to minutes and seconds
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d : %02d", minutes, seconds);

                // Update countdownDisplay with the formatted time
                countdownDisplay.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                countdownDisplay.setText("03 : 00");
                showProgressBar();
                resetActivityAfterDelay();
            }
        };

        countDownTimer.start(); // Start the countdown timer
    }

    private void showProgressBar() {
        circularProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void resetActivityAfterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Restart the activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }, 3000); // Delay of 3 seconds to show the progress bar (adjust as needed)
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
                redFragment.closeFragment();
                enableHomeScreen();
                isRedFragmentOpened = false;
                return;
            }
        }

        if (isBlueFragmentOpened) {
            blue_order blueFragment = (blue_order) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (blueFragment != null && blueFragment.isVisible()) {
                blueFragment.closeFragment();
                enableHomeScreen();
                isBlueFragmentOpened = false;
                return;
            }
        }

        super.onBackPressed();
    }

}


