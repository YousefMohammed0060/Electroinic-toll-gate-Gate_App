package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowCarActivity extends AppCompatActivity {
    TextView carModel, plateNumber;
    DatabaseReference carRef;

    String userKey ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_car);
        inti();
        LoadData();
    }

    private void LoadData() {
        carRef.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    carModel.append(snapshot.child("CarModel").getValue().toString());
                    plateNumber.append(snapshot.child("CarLetters").getValue().toString()+" "+snapshot.child("CarNumbers").getValue().toString());
                }else {
                    carModel.setText("User Don't have Car");
                    carModel.setTextColor(ContextCompat.getColor(ShowCarActivity.this, R.color.red));
                    plateNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inti() {
        carModel=findViewById(R.id.carModel);
        plateNumber=findViewById(R.id.plateNumber);
        carRef = FirebaseDatabase.getInstance().getReference().child("Cars");
        userKey = getIntent().getStringExtra("userKey");
    }
}