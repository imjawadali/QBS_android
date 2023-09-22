package com.example.shakerapplication.Adapters;

import android.content.Context;
import android.text.Layout;
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

public class Serial_Registration_Adapter extends  RecyclerView.Adapter<Serial_Registration_Adapter.ViewHolder> {

    private  List<String[]> dataList = new ArrayList<>();

    private OnItemClickListener mListener;


    @NonNull
    @Override
    public Serial_Registration_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.goods_issue_screen_card,parent,false);
        return new Serial_Registration_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Serial_Registration_Adapter.ViewHolder holder, int position) {
        String[] data = dataList.get(position);

        holder.sno.setText(data[0]);
        holder.material.setText(data[1]);
        holder.materialdescription.setText(data[2]);
        holder.serialno.setText(data[3]);


        holder.delete.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            dataList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            mListener.onItemClick(adapterPosition);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void addItem(String sno, String material,  String materialdescription,String serialno) {
//        description,
        String[] data = new String[]{sno, material, materialdescription,serialno};
        dataList.add(data);
        notifyItemInserted(dataList.size() - 1);
    }




    public void setOnItemClickListener(Serial_Registration_Adapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sno, material, materialdescription,serialno;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sno = itemView.findViewById(R.id.txt11);
            material = itemView.findViewById(R.id.txt22);
            materialdescription = itemView.findViewById(R.id.txt33);
            serialno = itemView.findViewById(R.id.txt44);
            delete = itemView.findViewById(R.id.deleteGI);

        }
    }

}