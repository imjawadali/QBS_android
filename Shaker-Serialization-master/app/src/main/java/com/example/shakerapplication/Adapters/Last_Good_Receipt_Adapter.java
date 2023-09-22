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

public class Last_Good_Receipt_Adapter extends RecyclerView.Adapter<Last_Good_Receipt_Adapter.ViewHolder> {
    List<String[]> dataList = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public Last_Good_Receipt_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.goodsreceiptlastscreencard,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Last_Good_Receipt_Adapter.ViewHolder holder, int position) {
        String[] data = dataList.get(position);

        holder.grsno.setText(data[0]);
        holder.grmaterial.setText(data[1]);
//        holder.description.setText(data[2]);
        holder.grdescr.setText(data[2]);
        holder.grtqy.setText(data[3]);


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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView grsno,grmaterial,grtqy,grdescr;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            grsno = itemView.findViewById(R.id.grsno);
            grmaterial = itemView.findViewById(R.id.grmaterial);
            grtqy = itemView.findViewById(R.id.grqty);
            grdescr = itemView.findViewById(R.id.grdescr);
        }
    }
}
