package com.example.communityapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.communityapplication.ModelClasses.Community_ModelClass;
import com.example.communityapplication.R;
import com.example.communityapplication.ViewHolder.ParticpentViewHolder;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticpentAdapter extends RecyclerView.Adapter<ParticpentAdapter.ViewHolder>{
    Context context;
    ArrayList<Community_ModelClass> participants = new ArrayList<>();

    public ParticpentAdapter(Context context, ArrayList<Community_ModelClass> participants) {
        this.context = context;
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticpentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_cart_community,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParticpentAdapter.ViewHolder holder, int position) {
        holder.text_name.setText(participants.get(position).getName());
        holder.text_date.setText(participants.get(position).getDate());
        holder.text_time.setText(participants.get(position).getTime());
        holder.text_detail.setText(participants.get(position).getDescription());
        Glide.with(context)
                .load(participants.get(position).getBottomimage1())
                .into(holder.imageone);
        Glide.with(context)
                .load(participants.get(position).getBottomimage2())
                .into(holder.imagetwo);
        Glide.with(context)
                .load(participants.get(position).getTopimage())
                .into(holder.profile_image);

    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView text_name,text_date,text_time,text_detail;
        ImageView imageone,imagetwo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            text_name = itemView.findViewById(R.id.text_name);
            text_date = itemView.findViewById(R.id.text_date);
            text_time = itemView.findViewById(R.id.text_time);
            text_detail = itemView.findViewById(R.id.text_detail);
            imageone = itemView.findViewById(R.id.imageone);
            imagetwo = itemView.findViewById(R.id.imagetwo);
        }
    }
}
