package com.asi.realmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    MaterialButton addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        addNoteButton = findViewById(R.id.addNoteBtn);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addnote.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Notes> notesList = realm.where(Notes.class).findAll().sort("createdTime", Sort.DESCENDING);

        RecyclerView recyclerView = findViewById(R.id.recyclerList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Notes>>() {
            @Override
            public void onChange(RealmResults<Notes> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}