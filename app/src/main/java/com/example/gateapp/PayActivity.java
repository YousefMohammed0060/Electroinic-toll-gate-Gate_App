package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PayActivity extends AppCompatActivity {

    private static final int GATE_PRICE = 10;
    int newWalletBalance;
    TextView payMessage, userNameTv, walletNameTv, walletBalanceTv,PriceTv;
    String userID;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef, walletRef, billRef,gateRef;

    ProgressDialog mLoadingBar;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        inti();
        checkResult();
    }

    private void inti() {
        payMessage = findViewById(R.id.payMessage);
        userNameTv = findViewById(R.id.userNameTv);
        walletNameTv = findViewById(R.id.walletNameTv);
        walletBalanceTv = findViewById(R.id.walletBalanceTv);
        PriceTv = findViewById(R.id.PriceTv);
        userID = getIntent().getStringExtra("userID");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        walletRef = FirebaseDatabase.getInstance().getReference().child("Wallets");
        billRef = FirebaseDatabase.getInstance().getReference().child("Bills");
        gateRef = FirebaseDatabase.getInstance().getReference().child("Gate");

        mLoadingBar = new ProgressDialog(this);
        mLoadingBar.setTitle("Pay");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();
    }

    private void checkResult() {
        walletRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int walletBalance = new Integer(snapshot.child("WalletBalance").getValue().toString());

                    if (walletBalance >= -10) {

                        newWalletBalance = walletBalance - GATE_PRICE;

                        HashMap hashMap = new HashMap();
                        hashMap.put("UserID", snapshot.child("UserID").getValue());
                        hashMap.put("WalletBalance", newWalletBalance);
                        hashMap.put("WalletName", snapshot.child("WalletName").getValue().toString());

                        walletRef.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                payMessage.setText("Operation Success");
                                payMessage.setTextColor(ContextCompat.getColor(PayActivity.this, R.color.Green));
                                createSuccessBill();
                                walletNameTv.setText("Wallet Name: " + snapshot.child("WalletName").getValue().toString());
                                walletBalanceTv.setText("Wallet Balance: " + newWalletBalance);
                                PriceTv.setText("Price: " + GATE_PRICE);

                                HashMap gateMap=new HashMap();
                                gateMap.put("status",1);
                                gateRef.updateChildren(gateMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                    }
                                });
                                endSession();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mLoadingBar.dismiss();
                                payMessage.setText(e.getMessage());
                                userNameTv.setVisibility(View.GONE);
                                walletNameTv.setVisibility(View.GONE);
                                walletBalanceTv.setVisibility(View.GONE);
                                PriceTv.setVisibility(View.GONE);
                                endSession();
                            }
                        });
                    } else {
                        mLoadingBar.dismiss();
                        createFailedBill();
                        payMessage.setText("Operation Failed");
                        payMessage.setTextColor(ContextCompat.getColor(PayActivity.this, R.color.red));
                        walletNameTv.setText("Wallet Name: " + snapshot.child("WalletName").getValue().toString());
                        walletBalanceTv.setText("Wallet Balance: " + newWalletBalance);
                        PriceTv.setText("Price: " + GATE_PRICE);

                        HashMap gateMap=new HashMap();
                        gateMap.put("status",0);
                        gateRef.updateChildren(gateMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                            }
                        });
                        endSession();
                    }
                }else {
                    mLoadingBar.dismiss();
                    payMessage.setText("Operation Failed"+"\n"+"Wallet not found");
                    payMessage.setTextColor(ContextCompat.getColor(PayActivity.this, R.color.red));
                    userNameTv.setVisibility(View.GONE);
                    walletNameTv.setVisibility(View.GONE);
                    walletBalanceTv.setVisibility(View.GONE);
                    endSession();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mLoadingBar.dismiss();
                payMessage.setText(error.getMessage());
                userNameTv.setVisibility(View.GONE);
                walletNameTv.setVisibility(View.GONE);
                walletBalanceTv.setVisibility(View.GONE);
                endSession();
            }
        });
    }

    private void endSession() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(PayActivity.this,MainActivity.class));
                finish();
            }
        }, 5000);
    }

    private void createSuccessBill() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        Date date1 = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String strDate1 = formatter1.format(date);

        mUserRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username=snapshot.child("username").getValue().toString();
                    userNameTv.setText("Username: " + username);
                    HashMap hashMap = new HashMap();
                    hashMap.put("userID", userID);
                    hashMap.put("username", username);
                    hashMap.put("price", GATE_PRICE);
                    hashMap.put("date", strDate1);
                    hashMap.put("status", "Success");
                    billRef.child(userID+" "+ strDate).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            mLoadingBar.dismiss();
                            Toast.makeText(PayActivity.this, "bill added", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mLoadingBar.dismiss();
                    Toast.makeText(PayActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mLoadingBar.dismiss();
                Toast.makeText(PayActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createFailedBill() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        Date date1 = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String strDate1 = formatter1.format(date);

        mUserRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username=snapshot.child("username").getValue().toString();
                    userNameTv.setText("Username: " + username);
                    HashMap hashMap = new HashMap();
                    hashMap.put("userID", userID);
                    hashMap.put("username", username);
                    hashMap.put("price", GATE_PRICE);
                    hashMap.put("date", strDate1);
                    hashMap.put("status", "Failed");
                    billRef.child(userID+" "+ strDate).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            mLoadingBar.dismiss();
                            Toast.makeText(PayActivity.this, "bill added", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mLoadingBar.dismiss();
                    Toast.makeText(PayActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mLoadingBar.dismiss();
                Toast.makeText(PayActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}