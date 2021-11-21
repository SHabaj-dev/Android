package com.asi.realmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class addnote extends AppCompatActivity {

    EditText titleInput, descriptionInput;
    MaterialButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        getSupportActionBar().hide();

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        titleInput = findViewById(R.id.editViewTitle);
        descriptionInput = findViewById(R.id.editTextDescription);
        saveButton = findViewById(R.id.saveBtn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long time = System.currentTimeMillis();

                realm.beginTransaction();
                Notes note = realm.createObject(Notes.class);
                note.setTitle(title);
                note.setDescription(description);
                note.setCreatedTime(time);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}