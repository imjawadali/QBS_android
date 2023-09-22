package com.example.communityapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communityapplication.R;
import com.example.communityapplication.ViewHolder.PersonViewHolder;

public class PersonDetaillAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    Context context;

    public PersonDetaillAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_person_detail, viewGroup, false);
        return new PersonViewHolder(view);      }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }
}

