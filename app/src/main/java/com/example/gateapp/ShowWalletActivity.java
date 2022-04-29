package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowWalletActivity extends AppCompatActivity {
    TextView walletName, walletBalance;
    DatabaseReference walletRef;

    String userKey ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wallet);
        inti();
        LoadData();
    }

    private void LoadData() {
        walletRef.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    walletName.append(snapshot.child("WalletName").getValue().toString());
                    walletBalance.append(snapshot.child("WalletBalance").getValue().toString()+" L.E");
                }else {
                    walletName.setText("User Don't have Wallet");
                    walletName.setTextColor(ContextCompat.getColor(ShowWalletActivity.this, R.color.red));
                    walletBalance.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inti() {
        walletName=findViewById(R.id.walletName);
        walletBalance=findViewById(R.id.walletBalance);
        walletRef = FirebaseDatabase.getInstance().getReference().child("Wallets");
        userKey = getIntent().getStringExtra("userKey");
    }
}