package com.example.gateapp.Users;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gateapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class userViewHolder extends RecyclerView.ViewHolder {
    public TextView usernameTv;
    public CircleImageView userImage;
    public userViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTv=itemView.findViewById(R.id.usernameTv);
        userImage=itemView.findViewById(R.id.userImage);
    }
}
