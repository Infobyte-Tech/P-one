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

public class red_order extends Fragment {
    private int value = 1; // Default value
    private int selectedValue = 100; // Default selected value

    private TextView redContractValue;
    private TextView redTotalCValue;

    private OnFragmentCloseListener onFragmentCloseListener;
    private OrderDbHelper dbHelper; // Database helper


    public interface OnFragmentCloseListener {
        void onClose();
    }

    public void setOnFragmentCloseListener(OnFragmentCloseListener listener) {
        this.onFragmentCloseListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red_order, container, false);

        dbHelper = new OrderDbHelper(getActivity()); // Initialize the database helper

        // Initialize UI elements
        redContractValue = view.findViewById(R.id.red_contract_value);
        redTotalCValue = view.findViewById(R.id.red_total_c_value);


        // Setup selection buttons
        setupSelectionButtons(view);

        // Setup increment and decrement buttons
        setupIncrementDecrementButtons(view);

        // Setup cancel button
        setupCancelButton(view);

        // Setup confirm button
        setupConfirmButton(view);


        return view;
    }

    // Method to set up selection buttons
    private void setupSelectionButtons(View view) {
        TextView redSelect500 = view.findViewById(R.id.red_select_500);
        TextView redSelect100 = view.findViewById(R.id.red_select_100);
        TextView redSelect50 = view.findViewById(R.id.red_select_50);

        // Set click listeners for selection buttons
        redSelect500.setOnClickListener(getSelectionClickListener(500));
        redSelect100.setOnClickListener(getSelectionClickListener(100));
        redSelect50.setOnClickListener(getSelectionClickListener(50));

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
        TextView redSelect500 = getView().findViewById(R.id.red_select_500);
        TextView redSelect100 = getView().findViewById(R.id.red_select_100);
        TextView redSelect50 = getView().findViewById(R.id.red_select_50);

        redSelect500.setBackgroundColor(selectedButton.getId() == R.id.red_select_500 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        redSelect100.setBackgroundColor(selectedButton.getId() == R.id.red_select_100 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
        redSelect50.setBackgroundColor(selectedButton.getId() == R.id.red_select_50 ? Color.parseColor("#EAE7E7") : Color.TRANSPARENT);
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

    private void setupConfirmButton(View view){

        TextView confirmButton = view.findViewById(R.id.red_confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating a new record in the local database for Red Order
                String token = generateToken();
                int totalContractValue = value * selectedValue;
                String text = "Red";

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
