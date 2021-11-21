package com.asi.realmdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    RealmResults<Notes> notesList;

    public MyAdapter(Context context, RealmResults<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notes note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());

        String formatTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.timeOutput.setText(formatTime);


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().equals("DELETE")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput, descriptionOutput, timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleOutput = itemView.findViewById(R.id.titleOutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionOutput);
            timeOutput = itemView.findViewById(R.id.timeOutput);
        }
    }
}
