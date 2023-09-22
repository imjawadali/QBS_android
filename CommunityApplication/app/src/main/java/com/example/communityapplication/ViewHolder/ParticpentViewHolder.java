package com.example.communityapplication.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communityapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticpentViewHolder extends RecyclerView.ViewHolder {
    TextView textname,textdate,texttime,textdetail;
    CircleImageView profileimage;
    ImageView imageones,imagetwoss;
    public ParticpentViewHolder(@NonNull View itemView) {
        super(itemView);

        textdate = itemView.findViewById(R
                .id.text_date);
        textname = itemView.findViewById(R.id.text_name);
        texttime = itemView.findViewById(R.id.text_time);
        profileimage = itemView.findViewById(R.id.profile_image);
        textdetail = itemView.findViewById(R.id.text_detail);
        imageones = itemView.findViewById(R.id.imageone);
        imagetwoss = itemView.findViewById(R.id.imagetwo);



    }
}

