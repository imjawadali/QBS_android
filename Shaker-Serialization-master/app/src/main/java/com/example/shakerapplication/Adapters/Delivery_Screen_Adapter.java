package com.example.shakerapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakerapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Delivery_Screen_Adapter extends RecyclerView.Adapter<Delivery_Screen_Adapter.ViewHolder> {
    List<String[]> delivery_screen_data = new ArrayList<>();
    Context context;

    public Delivery_Screen_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Delivery_Screen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.delivery_screen_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Delivery_Screen_Adapter.ViewHolder holder, int position) {
        String[] data = delivery_screen_data.get(position);
        holder.sno.setText(data[0]);
        holder.material.setText(data[1]);
        holder.materialdescription.setText(data[2]);
        holder.serialno.setText(data[3]);

    }

    @Override
    public int getItemCount() {
        return delivery_screen_data.size();
    }

    public void addItem(String sno, String material,  String materialdescription,String serialno) {
//        description,
        String[] data = new String[]{sno, material, materialdescription,serialno};
        delivery_screen_data.add(data);
        notifyItemInserted(delivery_screen_data.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sno,material,materialdescription,serialno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            material = itemView.findViewById(R.id.material);
            materialdescription = itemView.findViewById(R.id.materialdescription);
            serialno = itemView.findViewById(R.id.serialno);
        }
    }
}
