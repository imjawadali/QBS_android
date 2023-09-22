package com.example.shakerapplication.Adapters;

import android.content.Context;
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



public class Goods_Issue_Screen_Adapter extends RecyclerView.Adapter<Goods_Issue_Screen_Adapter.ViewHolder> {

    List<String[]> dataList = new ArrayList<>();
    Context context;
    private OnItemClickListener mListener;

    public Goods_Issue_Screen_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Goods_Issue_Screen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.goods_issue_screen_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Goods_Issue_Screen_Adapter.ViewHolder holder, int position) {
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



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

//    String description,

    public void addItem(String sno, String material,  String materialdescription,String serialno) {
//        description,
        String[] data = new String[]{sno, material, materialdescription,serialno};
        dataList.add(data);
        notifyItemInserted(dataList.size() - 1);
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








//public class Goods_Issue_Screen_Adapter extends RecyclerView.Adapter<Goods_Issue_Screen_Adapter.ViewHolder> {
//
////    Context context;
//
////    public Goods_Issue_Screen_Adapter(List<String> loadoutbounddeliveries,Context context) {
////        this.loadoutbounddeliveries = loadoutbounddeliveries;
////        this.context = context;
////    }
//
//    @NonNull
//    @Override
//    public Goods_Issue_Screen_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.goods_issue_screen_card,parent,false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Goods_Issue_Screen_Adapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 3;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        CardView addtolist;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//        }
//    }
//}
