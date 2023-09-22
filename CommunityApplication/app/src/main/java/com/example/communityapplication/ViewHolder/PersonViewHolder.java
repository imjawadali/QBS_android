package com.example.communityapplication.ViewHolder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.communityapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileimage;
    TextView textname;
    public PersonViewHolder(View itemView) {
        super(itemView);

        profileimage = itemView.findViewById(R.id.profile_image);
        textname = itemView.findViewById(R.id.text_name);
    }
}

