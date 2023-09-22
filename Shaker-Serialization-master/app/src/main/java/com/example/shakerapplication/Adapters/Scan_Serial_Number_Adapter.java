package com.example.shakerapplication.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakerapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Scan_Serial_Number_Adapter extends RecyclerView.Adapter<Scan_Serial_Number_Adapter.ViewHolder> {
    List<String[]> dataList = new ArrayList<>();
    Context context;
    private Scan_Serial_Number_Adapter.OnItemClickListener mListener;

    public Scan_Serial_Number_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Scan_Serial_Number_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.goods_receipt_serial_number_card,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
       return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Scan_Serial_Number_Adapter.ViewHolder holder, int position) {
        String[] data = dataList.get(position);
        holder.sno.setText(data[0]);
        holder.material.setText(data[1]);
        holder.materialdescription.setText(data[2]);
        holder.qty.setText(data[3]);


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                System.out.println("Pressed Delete at position: " + adapterPosition);
                dataList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                mListener.onItemClick(adapterPosition);
            }
        });
    }



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Scan_Serial_Number_Adapter.OnItemClickListener listener) {
        mListener = listener;
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addItem(String sno,String material,String materialdescription, String edittext) {

        String[] data = new String[]{sno,material,materialdescription,edittext};
        dataList.add(data);
        notifyItemInserted(dataList.size() - 1);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sno, material, materialdescription,qty;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            material = itemView.findViewById(R.id.material);
            materialdescription = itemView.findViewById(R.id.materialdescription);
            qty = itemView.findViewById(R.id.qty);
            delete = itemView.findViewById(R.id.deleteGRN);
        }
    }
}