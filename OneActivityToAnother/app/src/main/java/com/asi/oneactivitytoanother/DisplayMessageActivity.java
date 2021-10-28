package com.asi.oneactivitytoanother;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String mesg = intent.getStringExtra(MainActivity.Extra_Message);

        TextView textView = findViewById(R.id.textView);
        textView.setText(mesg);
    }
}