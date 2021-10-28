package com.asi.countincreaser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    Integer count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increase(View v){
        textview = findViewById(R.id.textview);

        count = Integer.parseInt(textview.getText().toString());
        count++;
        textview.setText(Integer.toString(count));
    }

    public void decrease(View v){
        textview = findViewById(R.id.textview);

        count = Integer.parseInt(textview.getText().toString());
        count--;
        textview.setText(Integer.toString(count));
    }

    public void toast(View v){
        Toast.makeText(getApplicationContext(), "Current Count is " + count, Toast.LENGTH_SHORT).show();
    }
}