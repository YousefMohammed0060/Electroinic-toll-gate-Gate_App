package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gateapp.Bills.ShowBillsActivity;
import com.example.gateapp.Users.UsersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity {

    TextView userID, username, userEmail, userNationalID, userPhone, userCity;
    ImageView userProfileImage;
    String userKey, Email, Password;

    FirebaseAuth mAuth;
    Task<Void> mUser;
    DatabaseReference mUserRef,walletRef,carRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        inti();
        LoadUserData();
    }

    private void LoadUserData() {
        mUserRef.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Email = snapshot.child("email").getValue().toString();
                    Password = snapshot.child("password").getValue().toString();
                    userID.append(snapshot.child("userId").getValue().toString());
                    username.append(snapshot.child("username").getValue().toString());
                    userEmail.append(snapshot.child("email").getValue().toString());
                    userNationalID.append(snapshot.child("nationalID").getValue().toString());
                    userPhone.append(snapshot.child("phone").getValue().toString());
                    userCity.append(snapshot.child("city").getValue().toString());
                    Picasso.get().load(snapshot.child("profileImage").getValue().toString()).into(userProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inti() {
        userID = findViewById(R.id.userID);
        username = findViewById(R.id.username);
        userEmail = findViewById(R.id.userEmail);
        userNationalID = findViewById(R.id.userNationalID);
        userPhone = findViewById(R.id.userPhone);
        userCity = findViewById(R.id.userCity);
        userProfileImage = findViewById(R.id.userProfileImage);
        userKey = getIntent().getStringExtra("userId");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        walletRef = FirebaseDatabase.getInstance().getReference().child("Wallets");
        carRef = FirebaseDatabase.getInstance().getReference().child("Cars");

    }

    public void removeUser(View view) {
        mAuth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                mUser=mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserDetailsActivity.this, "User Removed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mUserRef.child(userKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserDetailsActivity.this, "User is gone", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                walletRef.child(userKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UserDetailsActivity.this, "Wallet deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                carRef.child(userKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UserDetailsActivity.this, "Car deleted", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        startActivity(new Intent(UserDetailsActivity.this, UsersActivity.class));
    }

    public void getWallet(View view) {
        Intent intent=new Intent(UserDetailsActivity.this,ShowWalletActivity.class);
        intent.putExtra("userKey",userKey);
        startActivity(intent);
    }

    public void getCar(View view) {
        Intent intent=new Intent(UserDetailsActivity.this,ShowCarActivity.class);
        intent.putExtra("userKey",userKey);
        startActivity(intent);
    }

    public void getBills(View view) {
        Intent intent=new Intent(UserDetailsActivity.this, ShowBillsActivity.class);
        intent.putExtra("userKey",userKey);
        startActivity(intent);
    }
}