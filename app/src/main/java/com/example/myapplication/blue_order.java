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

    private int value = 1; // Default value
    private int selectedValue = 100; // Default selected value

    private TextView blueContractValue;
    private TextView blueTotalCValue;

    // Interface for fragment close events
    public interface OnFragmentCloseListener {
        void onClose();
    }

    private OnFragmentCloseListener onFragmentCloseListener;

    public void setOnFragmentCloseListener(OnFragmentCloseListener listener) {
        this.onFragmentCloseListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blue_order, container, false);

        // Initializing views
        blueContractValue = view.findViewById(R.id.blue_contract_value);
        blueTotalCValue = view.findViewById(R.id.blue_total_c_value);

        // Setting up selection buttons
        setupSelectionButtons(view);

        // Setting up increment and decrement buttons
        setupIncrementDecrementButtons(view);

        // Setting up cancel button
        setupCancelButton(view);

        return view;
    }

    private void setupSelectionButtons(View view) {
        // Finding selection buttons
        TextView blueSelect50 = view.findViewById(R.id.blue_select_50);
        TextView blueSelect100 = view.findViewById(R.id.blue_select_100);
        TextView blueSelect1000 = view.findViewById(R.id.blue_select_1000);

        // Setting click listeners and selecting default button
        blueSelect50.setOnClickListener(getSelectionClickListener(50));
        blueSelect100.setOnClickListener(getSelectionClickListener(100));
        blueSelect1000.setOnClickListener(getSelectionClickListener(1000));

        selectDefaultButtonAndContractValue(blueSelect100, value);
    }

    // Listener for selecting different values
    private View.OnClickListener getSelectionClickListener(final int selected) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedValue = selected;
                updateTotalContractValue();
                highlightSelectedButton(v);
            }
        };
    }

    // Method to select default button and set default value
    private void selectDefaultButtonAndContractValue(TextView defaultButton, int defaultValue) {
        defaultButton.setBackgroundColor(Color.parseColor("#EAE7E7"));
        blueContractValue.setText(String.valueOf(defaultValue));
    }

    // Highlighting selected button
    private void highlightSelectedButton(View selectedButton) {
        // Finding selection buttons
        TextView blueSelect50 = getView().findViewById(R.id.blue_select_50);
        TextView blueSelect100 = getView().findViewById(R.id.blue_select_100);
        TextView blueSelect1000 = getView().findViewById(R.id.blue_select_1000);

        // Highlighting selected button and resetting others
        blueSelect50.setBackgroundColor(selectedButton.getId() == R.id.blue_select_50 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        blueSelect100.setBackgroundColor(selectedButton.getId() == R.id.blue_select_100 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        blueSelect1000.setBackgroundColor(selectedButton.getId() == R.id.blue_select_1000 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
    }

    // Setting up increment and decrement buttons
    private void setupIncrementDecrementButtons(View view) {
        Button blueIncrementButton = view.findViewById(R.id.blue_incrementButton);
        Button blueDecrementButton = view.findViewById(R.id.blue_decrementButton);

        // Click listeners for increment and decrement buttons
        blueIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValueAndUpdate();
            }
        });

        blueDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValueAndUpdate();
            }
        });
    }

    // Incrementing value and updating UI
    private void incrementValueAndUpdate() {
        value++;
        updateContractValue();
    }

    // Decrementing value and updating UI
    private void decrementValueAndUpdate() {
        if (value > 1) {
            value--;
            updateContractValue();
        }
    }

    // Updating contract value and total contract value
    private void updateContractValue() {
        blueContractValue.setText(String.valueOf(value));
        updateTotalContractValue();
    }

    // Updating total contract value based on selected value and current value
    private void updateTotalContractValue() {
        int totalContractValue = value * selectedValue;
        blueTotalCValue.setText(String.valueOf(totalContractValue));
    }

    // Setting up cancel button
    private void setupCancelButton(View view) {
        TextView cancelButton = view.findViewById(R.id.blue_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
    }

    // Closing the fragment and notifying the listener
    public void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();

            if (onFragmentCloseListener != null) {
                onFragmentCloseListener.onClose();
                value = 1; // Resetting default value
            }
        }
    }
}
