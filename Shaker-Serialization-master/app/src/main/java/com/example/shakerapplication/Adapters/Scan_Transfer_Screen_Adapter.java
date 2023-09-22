package com.example.shakerapplication.Adapters;

import android.content.Context;
import android.media.Image;
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

public class Scan_Transfer_Screen_Adapter extends RecyclerView.Adapter<Scan_Transfer_Screen_Adapter.ViewHolder> {
    List<String[]> dataList = new ArrayList<>();
    Context context;
    private Scan_Transfer_Screen_Adapter.OnItemClickListener mListener;

    public Scan_Transfer_Screen_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Scan_Transfer_Screen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.scan_transfer_screen_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Scan_Transfer_Screen_Adapter.ViewHolder holder, int position) {
        String[] data = dataList.get(position);

        System.out.println("Data received: "+data[0]);
        System.out.println("Data received: "+data[1]);
        System.out.println("Data received: "+data[2]);
        System.out.println("Data received: "+data[3]);
        System.out.println("Data received: "+data[4]);

        holder.seqnum.setText(data[0]);
        holder.serialno.setText(data[1]);
        holder.current_storageloc.setText(data[2]);
        holder.current_plant.setText(data[3]);
        holder.model_descript.setText(data[4]);

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
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

    public void setOnItemClickListener(Scan_Transfer_Screen_Adapter.OnItemClickListener listener) {
        mListener = listener;
    }




    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addItem(String seq_num, String serial_num, String current_sto_loc, String current_plant, String model_descript) {

        String[] data = new String[]{seq_num, serial_num, current_sto_loc, current_plant, model_descript};
        dataList.add(data);
        notifyItemInserted(dataList.size() - 1);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView seqnum, model_descript , current_plant, current_storageloc, qty, serialno;
        ImageView deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seqnum = itemView.findViewById(R.id.seqnum);
            model_descript = itemView.findViewById(R.id.mdescript);
            current_storageloc = itemView.findViewById(R.id.cst);
            current_plant = itemView.findViewById(R.id.cp);
            serialno = itemView.findViewById(R.id.serialnum);
            deletebtn = itemView.findViewById(R.id.deleteSTL);
        }
    }
}
