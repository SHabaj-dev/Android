package com.asi.addingtwonumbers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText tv1, tv2;

    TextView tvr;

    Integer num1, num2, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void output(View v){
        getData();

        result = num1 + num2;
        tvr.append(Integer.toString(result));
    }

    public void getData(){
      tvr = findViewById(R.id.PResult);
      tv1 = findViewById(R.id.FirstNumber);
      tv2 = findViewById(R.id.SecondNumber);
      tvr.setText("");

      num1 = Integer.parseInt(tv1.getText().toString());
      num2 = Integer.parseInt(tv1.getText().toString());
    }
}