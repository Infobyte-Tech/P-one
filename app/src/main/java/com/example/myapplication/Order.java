package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Order extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        Button closeButton = view.findViewById(R.id.closeButton);

        // Set an OnClickListener for the "Close" button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        return view;
    }

    // Create a method to close the fragment
    private void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
        }
    }
}