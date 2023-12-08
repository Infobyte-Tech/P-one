package com.example.myapplication;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

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
        setContentView(R.layout.activity_home_screen);

//
//        public void onBackPressed () {
//            if (isRedFragmentOpened) {
//                red_order redFragment = (red_order) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//                if (redFragment != null && redFragment.isVisible()) {
//                    getSupportFragmentManager().beginTransaction().remove(redFragment).commit();
//                    enableHomeScreen();
//                    isRedFragmentOpened = false;
//                    return;
//                }
//            }
//
//            if (isBlueFragmentOpened) {
//                blue_order blueFragment = (blue_order) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//                if (blueFragment != null && blueFragment.isVisible()) {
//                    getSupportFragmentManager().beginTransaction().remove(blueFragment).commit();
//                    enableHomeScreen();
//                    isBlueFragmentOpened = false;
//                    return;
//                }
//            }
//
//            super.onBackPressed();
//        }
    }
}
