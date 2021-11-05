package com.asi.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {

    Context context;
    ArrayList<DataModel> arrayList;

    public Myadapter(Context context, ArrayList<DataModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, int position) {
        DataModel dataModel = arrayList.get(position);

        holder.first.setText(dataModel.name);
//        holder.last.setText(dataModel.);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView first, last ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.tvName);
            last = itemView.findViewById(R.id.tvName1);


        }
    }
}
