package com.example.communityapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communityapplication.ModelClasses.CommunityModel;
import com.example.communityapplication.ModelClasses.Community_ModelClass;
import com.example.communityapplication.ModelClasses.Result;
import com.example.communityapplication.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    ArrayList<Community_ModelClass> data = new ArrayList<>();
    Context context;

    public MovieAdapter(ArrayList<Community_ModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Community_ModelClass community_modelClass = data.get(position);
        holder.textoriginaltitle.setText(community_modelClass.getName());




    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
