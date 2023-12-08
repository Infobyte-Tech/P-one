package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class red_order extends Fragment {
    private int value = 1; // Default value
    private int selectedValue = 100; // Default selected value

    private TextView redContractValue;
    private TextView redTotalCValue;

    private OnFragmentCloseListener onFragmentCloseListener;

    public interface OnFragmentCloseListener {
        void onClose();
    }

    public void setOnFragmentCloseListener(OnFragmentCloseListener listener) {
        this.onFragmentCloseListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red_order, container, false);

        // Initialize UI elements
        redContractValue = view.findViewById(R.id.red_contract_value);
        redTotalCValue = view.findViewById(R.id.red_total_c_value);

        // Setup selection buttons
        setupSelectionButtons(view);

        // Setup increment and decrement buttons
        setupIncrementDecrementButtons(view);

        // Setup cancel button
        setupCancelButton(view);

        return view;
    }

    // Method to set up selection buttons
    private void setupSelectionButtons(View view) {
        TextView redSelect50 = view.findViewById(R.id.red_select_50);
        TextView redSelect100 = view.findViewById(R.id.red_select_100);
        TextView redSelect1000 = view.findViewById(R.id.red_select_1000);

        // Set click listeners for selection buttons
        redSelect50.setOnClickListener(getSelectionClickListener(50));
        redSelect100.setOnClickListener(getSelectionClickListener(100));
        redSelect1000.setOnClickListener(getSelectionClickListener(1000));

        // Select default button and set initial contract value
        selectDefaultButtonAndContractValue(redSelect100, value);
    }

    // Method to get click listener for selection buttons
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

    // Method to select default button and set initial contract value
    private void selectDefaultButtonAndContractValue(TextView defaultButton, int defaultValue) {
        defaultButton.setBackgroundColor(Color.parseColor("#EAE7E7"));
        redContractValue.setText(String.valueOf(defaultValue));
    }

    // Method to highlight the selected button
    private void highlightSelectedButton(View selectedButton) {
        TextView redSelect50 = getView().findViewById(R.id.red_select_50);
        TextView redSelect100 = getView().findViewById(R.id.red_select_100);
        TextView redSelect1000 = getView().findViewById(R.id.red_select_1000);

        redSelect50.setBackgroundColor(selectedButton.getId() == R.id.red_select_50 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        redSelect100.setBackgroundColor(selectedButton.getId() == R.id.red_select_100 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        redSelect1000.setBackgroundColor(selectedButton.getId() == R.id.red_select_1000 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
    }

    // Method to set up increment and decrement buttons
    private void setupIncrementDecrementButtons(View view) {
        Button redIncrementButton = view.findViewById(R.id.red_incrementButton);
        Button redDecrementButton = view.findViewById(R.id.red_decrementButton);

        // Set click listeners for increment and decrement buttons
        redIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValueAndUpdate();
            }
        });

        redDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValueAndUpdate();
            }
        });
    }

    // Method to increment value and update contract value
    private void incrementValueAndUpdate() {
        value++;
        updateContractValue();
    }

    // Method to decrement value and update contract value
    private void decrementValueAndUpdate() {
        if (value > 1) {
            value--;
            updateContractValue();
        }
    }

    // Method to update contract value
    private void updateContractValue() {
        redContractValue.setText(String.valueOf(value));
        updateTotalContractValue();
    }

    // Method to update total contract value
    private void updateTotalContractValue() {
        int totalContractValue = value * selectedValue;
        redTotalCValue.setText(String.valueOf(totalContractValue));
    }

    // Method to set up cancel button
    private void setupCancelButton(View view) {
        TextView cancelButton = view.findViewById(R.id.red_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
    }

    // Method to close the fragment
    public void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();

            // Notify fragment close listener and reset values
            if (onFragmentCloseListener != null) {
                onFragmentCloseListener.onClose();
                value = 1;
                selectedValue = 100;
            }
        }
    }
}
