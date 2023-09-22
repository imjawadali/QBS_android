package com.example.communityapplication.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communityapplication.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public TextView textoriginaltitle;
    public ImageView imagebackdrop;


    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        textoriginaltitle = itemView.findViewById(R.id.text_original_title);
        imagebackdrop = itemView.findViewById(R.id.image_backdrop);

    }
}
