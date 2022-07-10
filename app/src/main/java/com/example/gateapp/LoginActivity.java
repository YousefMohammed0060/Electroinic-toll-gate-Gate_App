package com.example.gateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gateapp.Users.UsersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
//
    EditText LoginEmail,LoginPassword;

    FirebaseAuth mAuth;
    DatabaseReference mAdminRef;
    ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inti();
    }

    private void inti() {
        LoginEmail=findViewById(R.id.LoginEmail);
        LoginPassword=findViewById(R.id.LoginPassword);

        mAuth = FirebaseAuth.getInstance();
        mAdminRef = FirebaseDatabase.getInstance().getReference().child("Admins");
        mLoadingBar=new ProgressDialog(this);
    }

    public void Login(View view) {
        String Email=LoginEmail.getText().toString();
        String Password=LoginPassword.getText().toString();

        if(Email.isEmpty() || !Email.contains("@") ||!Email.contains(".")){
            showError(LoginEmail,"Email is not valid");
        }else if (Password.isEmpty() || Password.length()<6){
            showError(LoginPassword,"Password is too short");
        }else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait,While check your data");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            if (Email.equals("myousef049@gmail.com")||Email.equals("muhamedelsayed2211@gmail.com")){
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mLoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, UsersActivity.class));
                        }else {
                            mLoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                mLoadingBar.dismiss();
                Toast.makeText(this, "There is no admin with this data", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showError(EditText field, String errorText) {
        field.setError(errorText);
        field.requestFocus();
    }


}