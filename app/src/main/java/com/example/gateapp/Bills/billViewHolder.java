package com.example.gateapp.Bills;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gateapp.R;

public class billViewHolder extends RecyclerView.ViewHolder {
    TextView BillStatus,BillName,BillDate,BillPrice;
    public billViewHolder(@NonNull View itemView) {
        super(itemView);
        BillStatus=itemView.findViewById(R.id.BillStatus);
        BillName=itemView.findViewById(R.id.BillName);
        BillDate=itemView.findViewById(R.id.BillDate);
        BillPrice=itemView.findViewById(R.id.BillPrice);
    }
}
