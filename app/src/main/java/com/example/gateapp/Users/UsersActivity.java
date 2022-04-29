package com.example.gateapp.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gateapp.R;
import com.example.gateapp.UserDetailsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UsersActivity extends AppCompatActivity {

    RecyclerView usersRv;
    DatabaseReference mUserRef;
    FirebaseRecyclerAdapter<usersModel, userViewHolder> adapter;
    FirebaseRecyclerOptions<usersModel> options;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        inti();
        LoadUsers();
    }

    private void LoadUsers() {
        options = new FirebaseRecyclerOptions.Builder<usersModel>().setQuery(mUserRef, usersModel.class).build();
        adapter=new FirebaseRecyclerAdapter<usersModel, userViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull userViewHolder holder, int position, @NonNull usersModel model) {
                holder.usernameTv.setText(" "+model.username);
                Picasso.get().load(model.profileImage).into(holder.userImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(UsersActivity.this, model.userId, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UsersActivity.this, UserDetailsActivity.class);
                        intent.putExtra("userId",model.userId);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
                return new userViewHolder(view);
            }
        };
        adapter.startListening();
        usersRv.setAdapter(adapter);
    }

    private void inti() {
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        usersRv=findViewById(R.id.usersRv);
        usersRv.setLayoutManager(new LinearLayoutManager(this));
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }
}