package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class blue_order extends Fragment {
    int value = 1; // Default value

    private OnFragmentCloseListener onFragmentCloseListener;
    public interface OnFragmentCloseListener {
        void onClose();
    }
    public void setOnFragmentCloseListener(blue_order.OnFragmentCloseListener listener) {
        this.onFragmentCloseListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blue_order, container, false);
        TextView blueContractValue = view.findViewById(R.id.blue_contract_value);
        Button blueIncrementButton = view.findViewById(R.id.blue_incrementButton);
        Button blueDecrementButton = view.findViewById(R.id.blue_decrementButton);
        TextView blueSelect50 = view.findViewById(R.id.blue_select_50);
        TextView blueSelect100 = view.findViewById(R.id.blue_select_100);
        TextView blueSelect1000 = view.findViewById(R.id.blue_select_1000);

        blueContractValue.setText(String.valueOf(value));

        blueSelect50.setBackgroundColor(Color.parseColor("#EAE7E7"));
        blueSelect50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueSelect50.setBackgroundColor(Color.parseColor("#EAE7E7"));
                blueSelect100.setBackgroundColor(Color.TRANSPARENT);
                blueSelect1000.setBackgroundColor(Color.TRANSPARENT);
                // Perform actions for selection of "50"
            }
        });
        blueSelect100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueSelect50.setBackgroundColor(Color.TRANSPARENT);
                blueSelect100.setBackgroundColor(Color.parseColor("#EAE7E7"));
                blueSelect1000.setBackgroundColor(Color.TRANSPARENT);
                // Perform actions for selection of "100"
            }
        });
        blueSelect1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blueSelect50.setBackgroundColor(Color.TRANSPARENT);
                blueSelect100.setBackgroundColor(Color.TRANSPARENT);
                blueSelect1000.setBackgroundColor(Color.parseColor("#EAE7E7"));
                // Perform actions for selection of "1000"
            }
        });
        blueIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                blueContractValue.setText(String.valueOf(value)); // Update TextView
            }
        });
        blueDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value > 1) { // Check if value is greater than 1
                    value--;// Increment value
                    blueContractValue.setText(String.valueOf(value)); // Update TextView
                }
            }
        });

        TextView cancelButton = view.findViewById(R.id.blue_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        return view;
    }
    private void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();

            if (onFragmentCloseListener != null) {
                onFragmentCloseListener.onClose();
                Toast.makeText(getActivity().getApplicationContext(), "Value set to default: ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}