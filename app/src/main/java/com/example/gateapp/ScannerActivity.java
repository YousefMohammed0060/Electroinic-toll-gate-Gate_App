package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.gateapp.CarDetails.CarsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScannerActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    QRCodeReaderView qrCodeReaderView;
    String aiPlate, userPlate, userId;
    Toolbar toolbar;
    List<CarsModel> carsModels = new ArrayList<>();

    DatabaseReference mUserRef, walletRef, mAdminRef, carRef, aiPlatesRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();
        cameraPermission();
        AiCarValidation();


    }

    private void AiCarValidation() {
        carRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                carsModels.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    CarsModel carsModel = postSnapshot.getValue(CarsModel.class);
                    carsModels.add(carsModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScannerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        aiPlatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aiPlate = snapshot.child("plate").getValue().toString();
                aiPlate = aiPlate.replaceAll("\\s+", "");

                for (int i = 0; i < carsModels.size(); i++) {
                    userPlate = carsModels.get(i).getFullPlateNO();
                    userPlate = userPlate.replaceAll("\\s+", "");
                    if (userPlate.equals(aiPlate)) {
                        userId = carsModels.get(i).getUserID();
                        Toast.makeText(ScannerActivity.this, "Just Sec", Toast.LENGTH_SHORT).show();
                        HashMap hashMap = new HashMap();
                        hashMap.put("plate", "Empty");
                        aiPlatesRef.updateChildren(hashMap);
                        Intent intent = new Intent(ScannerActivity.this, PayActivity.class);
                        intent.putExtra("userID", carsModels.get(i).getUserID());
                        startActivity(intent);
                        finish();

                    } else if (aiPlate.equals("Empty")) {

                    } else {
                        Toast.makeText(ScannerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void inti() {
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener((QRCodeReaderView.OnQRCodeReadListener) ScannerActivity.this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setFrontCamera();

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scan and go");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.login);

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAdminRef = FirebaseDatabase.getInstance().getReference().child("Admins");
        walletRef = FirebaseDatabase.getInstance().getReference().child("Wallets");
        aiPlatesRef = FirebaseDatabase.getInstance().getReference().child("Ai_plates");
        carRef = FirebaseDatabase.getInstance().getReference().child("Cars");


    }


    private void cameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ScannerActivity.this, LoginActivity.class));
        }
        return true;
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Intent intent = new Intent(ScannerActivity.this, PayActivity.class);
        intent.putExtra("userID", text);
        startActivity(intent);
        finish();
    }
}