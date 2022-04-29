package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    QRCodeReaderView qrCodeReaderView;
    String userID;
    Toolbar toolbar;


    DatabaseReference mUserRef, walletRef,mAdminRef;

//    String city,nationalID,phone,profileImage,username,ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();
        cameraPermission();
//        AddAdmin();
    }



//    private void AddAdmin() {
//        mUserRef.child("YZr5Skt5j8NEeFjZnRnq54pZLjE2").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                username=snapshot.child("username").getValue().toString();
//                profileImage=snapshot.child("profileImage").getValue().toString();
//                phone=snapshot.child("phone").getValue().toString();
//                nationalID=snapshot.child("nationalID").getValue().toString();
//                city=snapshot.child("city").getValue().toString();
//
//                HashMap hashMap=new HashMap();
//                hashMap.put("username",username);
//                hashMap.put("profileImage",profileImage);
//                hashMap.put("phone",phone);
//                hashMap.put("nationalID",nationalID);
//                hashMap.put("city",city);
//                hashMap.put("email","muhamedelsayed2211@gmail.com");
//                hashMap.put("password",snapshot.child("password").getValue().toString());
//                hashMap.put("userId",snapshot.child("userId").getValue().toString());
//
//                mAdminRef.child("YZr5Skt5j8NEeFjZnRnq54pZLjE2").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void inti() {
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener((QRCodeReaderView.OnQRCodeReadListener) MainActivity.this);
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
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        return true;
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Intent intent = new Intent(MainActivity.this, PayActivity.class);
        intent.putExtra("userID", text);
        startActivity(intent);
        finish();
    }
}