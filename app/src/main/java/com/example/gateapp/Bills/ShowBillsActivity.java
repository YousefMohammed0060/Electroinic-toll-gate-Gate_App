package com.example.gateapp.Bills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gateapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowBillsActivity extends AppCompatActivity {

    RecyclerView billsRv;


    String userKey;
    DatabaseReference billRef;
    FirebaseRecyclerAdapter<billModel, billViewHolder> adapter;
    FirebaseRecyclerOptions<billModel> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bills);
        inti();
        LoadBills();
    }

    private void inti() {
        userKey = getIntent().getStringExtra("userKey");
        billRef = FirebaseDatabase.getInstance().getReference().child("Bills");
        billsRv=findViewById(R.id.billsRv);
        billsRv.setLayoutManager(new LinearLayoutManager(ShowBillsActivity.this));
    }
    private void LoadBills() {
        options = new FirebaseRecyclerOptions.Builder<billModel>().setQuery(billRef, billModel.class).build();
        adapter=new FirebaseRecyclerAdapter<billModel, billViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull billViewHolder holder, int position, @NonNull billModel model) {
                if (userKey.equals(model.getUserID())){
                    if (model.getStatus().equals("Success")){
                        holder.BillStatus.setText(model.getStatus());
                        holder.BillStatus.setTextColor(ContextCompat.getColor(ShowBillsActivity.this, R.color.Green));
                        holder.BillName.setText(model.getUsername());
                        holder.BillDate.setText(model.getDate());
                        holder.BillPrice.setText(model.getPrice()+" L.E");
                    }else if (model.getStatus().equals("Failed")){
                        holder.BillStatus.setText(model.getStatus());
                        holder.BillStatus.setTextColor(ContextCompat.getColor(ShowBillsActivity.this, R.color.red));
                        holder.BillName.setText(model.getUsername());
                        holder.BillDate.setText(model.getDate());
                        holder.BillPrice.setText(model.getPrice()+" L.E");
                    }

                }else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }

            @NonNull
            @Override
            public billViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bill_item, parent, false);
                return new billViewHolder(view);
            }
        };
        adapter.startListening();
        billsRv.setAdapter(adapter);
    }
}