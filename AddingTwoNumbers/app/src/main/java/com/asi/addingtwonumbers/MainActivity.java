package com.asi.addingtwonumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button Add;
    EditText tv1, tv2;

    TextView tvr;
    int num1, num2,res;

    String a = "", b = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Add = findViewById(R.id.Add);
        tv1 = findViewById(R.id.FirstNumber);
        tv2 = findViewById(R.id.SecondNumber);
        tvr = findViewById(R.id.PResult);


        a = tv1.getText().toString();
        b = tv2.getText().toString();
        num1 = Integer.parseInt(a);
        num2 = Integer.parseInt(b);
        tvr.setText("");

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res = num1 + num2;
                tvr.setText(Integer.toString(res));
            }
        });
    }
}