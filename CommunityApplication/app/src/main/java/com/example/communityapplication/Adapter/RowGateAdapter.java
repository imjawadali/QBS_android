package com.example.communityapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communityapplication.R;
import com.example.communityapplication.ViewHolder.RowGateViewHolder;

public class RowGateAdapter  extends RecyclerView.Adapter<RowGateViewHolder> {

    Context context;

    public RowGateAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RowGateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_gate_update, parent, false);
        return new RowGateViewHolder(view);       }

    @Override
    public void onBindViewHolder(@NonNull RowGateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }
}


