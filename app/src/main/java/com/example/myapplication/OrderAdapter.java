package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private List<Order> orderList;
    private Context context;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_row_recyclerview, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Set data to the views in the ViewHolder
        holder.tokenTextView.setText(order.getToken());
        holder.valueTextView.setText(String.valueOf(order.getContractValue()));
        holder.colorTextView.setText(order.getText());
        // Set the text color based on the value of order.getText()
        if (order.getText().equals("Red")) {
            holder.colorTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_dark));
        } else if (order.getText().equals("Blue")) {
            holder.colorTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_blue_dark));
        } else {
            // Set a default color if needed
            holder.colorTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
        }

        // Set the result text as needed (might need to fetch this from the database)
        holder.resultTextView.setText("NULL");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
