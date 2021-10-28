package com.asi.learntable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView;
    String input;

    Integer num, result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printTable(View v){
        getdata();
        num = Integer.parseInt(input);

        for(int i = 1; i <= 10; i++){
            result = num * i;
            String myChar = num + " * " + i + " = " + result + "\n";
            show(myChar);
        }

    }

    public void getdata(){
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textView.setText("");
        input = editText.getText().toString().trim();

    }

    public void show(String table){
        textView.append(table);
    }
}