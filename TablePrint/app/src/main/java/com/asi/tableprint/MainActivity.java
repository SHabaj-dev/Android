package com.asi.tableprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonShow;
    EditText inputNumber;
    TextView showTable;
    Integer num;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        buttonShow = findViewById(R.id.tableBtn);
        inputNumber = findViewById(R.id.enterNumber);
        showTable = findViewById(R.id.tableView);



        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNum = inputNumber.getText().toString();
                num = Integer.parseInt(strNum);
                printTable(num);
            }
        });

    }

    public  void printTable(int n){

        for (int i = 1; i <= 10; i++){
            str = String.valueOf((n * i));

            showTable.append(n +" x " + i + " = " + str + "\n");
        }
    }
}