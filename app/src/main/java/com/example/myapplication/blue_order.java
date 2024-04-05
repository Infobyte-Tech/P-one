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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class blue_order extends Fragment {

    private int value = 1; // Default value
    private int selectedValue = 100; // Default selected value

    private TextView blueContractValue;
    private TextView blueTotalCValue;
    private OrderDbHelper dbHelper; // Database helper


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

        dbHelper = new OrderDbHelper(getActivity()); // Initialize the database helper

        // Initializing views
        blueContractValue = view.findViewById(R.id.blue_contract_value);
        blueTotalCValue = view.findViewById(R.id.blue_total_c_value);

        // Setting up selection buttons
        setupSelectionButtons(view);

        // Setting up increment and decrement buttons
        setupIncrementDecrementButtons(view);

        // Setting up cancel button
        setupCancelButton(view);

        setupConfirmButton(view);


        return view;
    }

    private void setupSelectionButtons(View view) {
        // Finding selection buttons
        TextView blueSelect500 = view.findViewById(R.id.blue_select_500);
        TextView blueSelect100 = view.findViewById(R.id.blue_select_100);
        TextView blueSelect50 = view.findViewById(R.id.blue_select_50);

        // Setting click listeners and selecting default button
        blueSelect500.setOnClickListener(getSelectionClickListener(500));
        blueSelect100.setOnClickListener(getSelectionClickListener(100));
        blueSelect50.setOnClickListener(getSelectionClickListener(50));

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
        TextView blueSelect500 = getView().findViewById(R.id.blue_select_500);
        TextView blueSelect100 = getView().findViewById(R.id.blue_select_100);
        TextView blueSelect50 = getView().findViewById(R.id.blue_select_50);

        // Highlighting selected button and resetting others
        blueSelect500.setBackgroundColor(selectedButton.getId() == R.id.blue_select_500 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        blueSelect100.setBackgroundColor(selectedButton.getId() == R.id.blue_select_100 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        blueSelect50.setBackgroundColor(selectedButton.getId() == R.id.blue_select_50 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
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

    private void setupConfirmButton(View view){

        TextView confirmButton = view.findViewById(R.id.blue_confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating a new record in the local database for Red Order
                String token = generateToken();
                int totalContractValue = value * selectedValue;
                String text = "Blue";

                // Insert into the local database using the helper
                long result = dbHelper.addOrder(token, totalContractValue, text);
                if (result != -1) {
                    // Insertion successful
                    // You can perform other actions if needed
                    Toast.makeText(getContext(), "Your message here", Toast.LENGTH_SHORT).show();
                    closeFragment();
                } else {
                    // Insertion failed
                    // Handle the failure scenario
                }

                // Close the fragment or perform other actions
            }
        });
    }

    private String generateToken() {
        // Get today's date in a specific format (e.g., YYYYMMDD)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        // Generate a random 4-digit number with an alphabet
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // Generates a 4-digit random number
        char randomAlphabet = (char) (random.nextInt(26) + 'A'); // Generates a random alphabet

        // Combine the current date and random number to create a token
        return currentDate + randomAlphabet + randomNumber;
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
