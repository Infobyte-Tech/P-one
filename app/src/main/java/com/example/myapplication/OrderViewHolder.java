package com.example.myapplication;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView tokenTextView;
    TextView valueTextView;
    TextView colorTextView;
    TextView resultTextView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        tokenTextView = itemView.findViewById(R.id.textvalue_Token);
        valueTextView = itemView.findViewById(R.id.textvalue_Value);
        colorTextView = itemView.findViewById(R.id.textvalue_Color);
        resultTextView = itemView.findViewById(R.id.textvalue_Result);
    }
}
